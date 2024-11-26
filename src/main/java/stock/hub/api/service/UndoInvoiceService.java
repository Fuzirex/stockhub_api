package stock.hub.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.UndoInvoiceRequestDTO;
import stock.hub.api.model.entity.Invoice;
import stock.hub.api.model.entity.Item;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.model.entity.pk.ItemPK;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.ExceptionType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.model.type.StockStatusType;
import stock.hub.api.repository.InvoiceRepository;
import stock.hub.api.repository.ItemRepository;
import stock.hub.api.repository.StockRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UndoInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;

    @Transactional(rollbackOn = Exception.class)
    public void undoInvoiceOperation(UndoInvoiceRequestDTO dto) {
        Item item = findAndValidateItem(dto);

        updateStock(item);
        deleteItemAndRemoveInvoiceIfNoItemsLeft(item);
    }

    private void deleteItemAndRemoveInvoiceIfNoItemsLeft(Item item) {
        Invoice invoice = item.getPk().getInvoice();

        itemRepository.delete(item);

        if (!itemRepository.existsById(new ItemPK(invoice)))
            invoiceRepository.delete(invoice);
    }

    private void updateStock(Item item) {
        Invoice invoice = item.getPk().getInvoice();
        Stock stock = item.getPk().getStock();
        InvoiceOperationType operationType = invoice.getPk().getOperationType();

        switch (operationType) {
            case SALE -> stock.setStatus(StockStatusType.AVAILABLE);
            case RETURN -> stock.setStatus(StockStatusType.SOLD);
            case TRANSFER -> {
                stock.setDealer(invoice.getPk().getDealer());
                stock.setStatus(StockStatusType.AVAILABLE);
            }
        }

        stockRepository.save(stock);
    }

    private Item findAndValidateItem(UndoInvoiceRequestDTO dto) {
        Item item = itemRepository.findItemByPK(dto.getChassisNumber(), dto.getDealerCNPJ(), dto.getInvoiceNumber(), dto.getInvoiceSeries(), dto.getOperationType());
        if (Objects.isNull(item))
            throw new StockHubException(ExceptionType.INVOICE_NOT_FOUND);

        List<Item> recentInvoices = itemRepository.findAllInvoiceItemByChassisNumberOrderByEmissionDateDesc(dto.getChassisNumber());
        if (recentInvoices.stream().anyMatch(it -> it.getPk().getInvoice().getEmissionDate().isAfter(item.getPk().getInvoice().getEmissionDate())))
            throw new StockHubException(ExceptionType.MORE_RECENT_INVOICE_ALREADY_EXISTS);

        return item;
    }
}
