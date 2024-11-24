package stock.hub.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.InvoiceEntryRequestDTO;
import stock.hub.api.model.dto.request.InvoiceEntryStockRequestDTO;
import stock.hub.api.model.dto.response.CityResponseDTO;
import stock.hub.api.model.dto.response.CountryResponseDTO;
import stock.hub.api.model.dto.response.StateResponseDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Item;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.model.entity.pk.InvoicePK;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.ExceptionType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.model.type.StockStatusType;
import stock.hub.api.repository.InvoiceRepository;
import stock.hub.api.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceEntryService {

    private final InvoiceRepository invoiceRepository;
    private final ItemRepository itemRepository;

    private final ContextService contextService;
    private final DealerService dealerService;
    private final StockService stockService;
    private final LocationService locationService;

    @Transactional(rollbackOn = Exception.class)
    public void processInvoiceEntry(InvoiceEntryRequestDTO dto) {
        validateInvoiceInfoForInvoiceEntry(dto);

        List<Stock> stockList = validateAndFindStockForInvoiceEntry(dto);
        Invoice invoice = validateAndCreateInvoiceFromInvoiceEntryDTO(dto, stockList);

        validateMostRecentInvoice(dto, stockList);

        invoiceRepository.save(invoice);
        updateStockForInvoiceEntry(invoice, stockList);
    }

    private void updateStockForInvoiceEntry(Invoice invoice, List<Stock> stockList) {
        for (Stock stock : stockList) {
            if (invoice.getPk().getOperationType() == InvoiceOperationType.SALE)
                stock.setStatus(StockStatusType.SOLD);

            if (invoice.getPk().getOperationType() == InvoiceOperationType.RETURN)
                stock.setStatus(StockStatusType.AVAILABLE);

            if (invoice.getPk().getOperationType() == InvoiceOperationType.TRANSFER)
                stock.setDealer(invoice.getDealerToTransfer());
        }

        stockService.saveStockList(stockList);
    }

    private void validateMostRecentInvoice(InvoiceEntryRequestDTO dto, List<Stock> stockList) {
        for (Stock stock : stockList) {
            List<Item> lastInvoices = itemRepository.findAllInvoiceItemByChassisNumberOrderByEmissionDateDesc(stock.getChassisNumber());

            if (CollectionUtils.isNotEmpty(lastInvoices)
                    && lastInvoices.getFirst().getPk().getInvoice().getEmissionDate().isAfter(dto.getEmissionDate()))
                throw new StockHubException(ExceptionType.MORE_RECENT_INVOICE_ALREADY_EXISTS);
        }
    }

    private Invoice validateAndCreateInvoiceFromInvoiceEntryDTO(InvoiceEntryRequestDTO dto, List<Stock> stockList) {
        Invoice invoice = findInvoiceByInvoiceEntryDTO(dto);

        if (Objects.nonNull(invoice))
            throw new StockHubException(ExceptionType.INVOICE_ALREADY_SUBMITTED);

        invoice = new Invoice();

        invoice.setPk(InvoicePK.builder()
                .number(dto.getInvoiceNumber())
                .series(dto.getInvoiceSeries())
                .operationType(dto.getOperationType())
                .dealer(Dealer.builder().cnpj(dto.getDealerCNPJ()).build())
                .build());

        invoice.setEmissionDate(dto.getEmissionDate());
        invoice.setCurrency(dto.getCurrencyType());
        invoice.setValue(dto.getInvoiceValue());

        if (dto.getOperationType() == InvoiceOperationType.SALE)
            validateAndPopulateCustomerInvoiceEntryInfo(dto, invoice);

        if (dto.getOperationType() == InvoiceOperationType.TRANSFER)
            validateAndPopulateDealerToTransferForInvoiceEntryInfo(dto, invoice);

        if (dto.getOperationType() == InvoiceOperationType.RETURN)
            validateAndPopulateCustomerReturnInvoiceEntryInfo(dto, invoice);

        Invoice finalInvoice = invoice;
        invoice.setItems(stockList.stream().map(stock -> new Item(finalInvoice, stock)).toList());

        return invoice;
    }

    private void validateAndPopulateCustomerReturnInvoiceEntryInfo(InvoiceEntryRequestDTO dto, Invoice invoice) {
        List<Item> lastItemSalesList = itemRepository.findLastSalesInvoicesByDealerAndChassisList(
                dto.getDealerCNPJ(), dto.getProducts().stream().map(InvoiceEntryStockRequestDTO::getChassisNumber).toList());

        if (CollectionUtils.isEmpty(lastItemSalesList))
            throw new StockHubException(ExceptionType.LAST_SALE_INVOICE_NOT_FOUND);

        Invoice lastSaleInvoice = lastItemSalesList.getFirst().getPk().getInvoice();

        for (InvoiceEntryStockRequestDTO product : dto.getProducts()) {
            Item thisProductLastSale = lastItemSalesList.stream().filter(sale ->
                    StringUtils.equals(sale.getPk().getStock().getChassisNumber(), product.getChassisNumber())).findFirst().orElse(null);

            if (Objects.isNull(thisProductLastSale)
                    || !thisProductLastSale.getPk().getInvoice().getPk().equals(lastSaleInvoice.getPk()))
                throw new StockHubException(ExceptionType.PRODUCTS_FROM_DIF_INVOICES);
        }

        if (dto.getEmissionDate().isBefore(lastSaleInvoice.getEmissionDate()))
            throw new StockHubException(ExceptionType.RETURN_DATE_BEFORE_LAST_SALE);

        invoice.setCustomerDocumentType(lastSaleInvoice.getCustomerDocumentType());
        invoice.setCustomerLegalNumber(lastSaleInvoice.getCustomerLegalNumber());
        invoice.setCustomerName(lastSaleInvoice.getCustomerName());
        invoice.setCustomerCountryID(lastSaleInvoice.getCustomerCountryID());
        invoice.setCustomerCountry(lastSaleInvoice.getCustomerCountry());
        invoice.setCustomerStateID(lastSaleInvoice.getCustomerStateID());
        invoice.setCustomerState(lastSaleInvoice.getCustomerState());
        invoice.setCustomerCityID(lastSaleInvoice.getCustomerCityID());
        invoice.setCustomerCity(lastSaleInvoice.getCustomerCity());
        invoice.setCustomerAddress(lastSaleInvoice.getCustomerAddress());
        invoice.setCustomerAddressComplement(lastSaleInvoice.getCustomerAddressComplement());
    }

    private void validateAndPopulateCustomerInvoiceEntryInfo(InvoiceEntryRequestDTO dto, Invoice invoice) {
        if (StringUtils.isBlank(dto.getCustomerName())
                || StringUtils.isBlank(dto.getCustomerLegalNumber())
                || Objects.isNull(dto.getCustomerDocumentType())
                || Objects.isNull(dto.getCustomerCountry())
                || Objects.isNull(dto.getCustomerState())
                || Objects.isNull(dto.getCustomerCity()))
            throw new StockHubException(ExceptionType.CUSTOMER_INFO_NOT_INFORMED);

        CountryResponseDTO country = locationService.getCountryByID(dto.getCustomerCountry());
        if (Objects.isNull(country))
            throw new StockHubException(ExceptionType.CUSTOMER_COUNTRY_NOT_FOUND);

        StateResponseDTO state = locationService.getStateByID(dto.getCustomerState());
        if (Objects.isNull(state))
            throw new StockHubException(ExceptionType.CUSTOMER_STATE_NOT_FOUND);

        CityResponseDTO city = locationService.getCityByID(dto.getCustomerCity());
        if (Objects.isNull(city))
            throw new StockHubException(ExceptionType.CUSTOMER_CITY_NOT_FOUND);

        invoice.setCustomerCountryID(country.getCode());
        invoice.setCustomerCountry(country.getDescription());
        invoice.setCustomerStateID(state.getCode());
        invoice.setCustomerState(state.getDescription());
        invoice.setCustomerCityID(city.getCode());
        invoice.setCustomerCity(city.getDescription());

        invoice.setCustomerDocumentType(dto.getCustomerDocumentType());
        invoice.setCustomerLegalNumber(dto.getCustomerLegalNumber());
        invoice.setCustomerName(dto.getCustomerName());
        invoice.setCustomerAddress(dto.getCustomerAddress());
        invoice.setCustomerAddressComplement(dto.getCustomerComplement());
    }

    private List<Stock> validateAndFindStockForInvoiceEntry(InvoiceEntryRequestDTO dto) {
        List<Stock> stockList = new ArrayList<>();

        for (InvoiceEntryStockRequestDTO prod : dto.getProducts()) {
            Stock stock = stockService.findByChassisNumberAndDealerCNPJ(prod.getChassisNumber(), dto.getDealerCNPJ());

            if (Objects.isNull(stock))
                throw new StockHubException(ExceptionType.PRODUCT_NOT_FOUND);

            if ((dto.getOperationType() == InvoiceOperationType.SALE || dto.getOperationType() == InvoiceOperationType.TRANSFER)
                    && stock.getStatus() != StockStatusType.AVAILABLE)
                throw new StockHubException(ExceptionType.INVALID_STOCK_STATUS);

            if (dto.getOperationType() == InvoiceOperationType.RETURN && stock.getStatus() != StockStatusType.SOLD)
                throw new StockHubException(ExceptionType.INVALID_STOCK_STATUS);

            stockList.add(stock);
        }

        return stockList;
    }

    private Invoice findInvoiceByInvoiceEntryDTO(InvoiceEntryRequestDTO dto) {
        return invoiceRepository.findById(
                        InvoicePK.builder()
                                .number(dto.getInvoiceNumber())
                                .series(dto.getInvoiceSeries())
                                .operationType(dto.getOperationType())
                                .dealer(Dealer.builder().cnpj(dto.getDealerCNPJ()).build())
                                .build())
                .orElse(null);
    }

    private void validateAndPopulateDealerToTransferForInvoiceEntryInfo(InvoiceEntryRequestDTO dto, Invoice invoice) {
        Dealer dealerToTransfer = dealerService.getDealerByCNPJ(dto.getDealerToTransfer());

        if (Objects.isNull(dealerToTransfer)
                || !dealerToTransfer.getActive()
                || StringUtils.equals(dealerToTransfer.getCnpj(), contextService.getCurrentDealer().getCnpj()))
            throw new StockHubException(ExceptionType.INVALID_DEALER_TO_TRANSFER);

        invoice.setDealerToTransfer(dealerToTransfer);
    }

    private void validateInvoiceInfoForInvoiceEntry(InvoiceEntryRequestDTO dto) {
        if (!StringUtils.equals(dto.getDealerCNPJ(), contextService.getCurrentDealer().getCnpj()))
            throw new StockHubException(ExceptionType.INVALID_DEALER);

        if (Objects.isNull(dto.getCurrencyType()) ^ Objects.isNull(dto.getInvoiceValue()))
            throw new StockHubException(ExceptionType.INVOICE_INFO_NOT_INFORMED);

        if (dto.getEmissionDate().isAfter(LocalDateTime.now()))
            throw new StockHubException(ExceptionType.EMISSION_DATE_AFTER_NOW);
    }

}
