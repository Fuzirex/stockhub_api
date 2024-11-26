package stock.hub.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.dto.report.BaseReportDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.model.entity.Item;
import stock.hub.api.model.entity.pk.ItemPK;
import stock.hub.api.model.type.InvoiceOperationType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemPK> {

    @Query("""
            select new stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO(
            ii.pk.invoice.pk.dealer.cnpj,
            ii.pk.invoice.pk.number,
            ii.pk.invoice.pk.series,
            ii.pk.invoice.pk.operationType,
            ii.pk.invoice.emissionDate,
            ii.pk.stock.chassisNumber,
            ii.pk.stock.commercialSeries,
            ii.pk.stock.itemCode,
            ii.pk.stock.model,
            ii.pk.stock.type.type,
            ii.pk.stock.type.translationPT,
            ii.pk.stock.type.translationEN,
            ii.pk.stock.type.translationES)
            from Item ii
            where ii.pk.invoice.pk.dealer.cnpj = :dealerCNPJ
            and ii.pk.invoice.emissionDate > :emissionPeriod
            and (:invoiceNumber is null or ii.pk.invoice.pk.number = :invoiceNumber)
            and (:operationType is null or ii.pk.invoice.pk.operationType = :operationType)
            and (:chassisNumber is null or ii.pk.stock.chassisNumber = :chassisNumber)
            and (:commercialSeries is null or ii.pk.stock.commercialSeries = :commercialSeries)
            and (:productModel is null or ii.pk.stock.model = :productModel)
            and (:itemCode is null or ii.pk.stock.itemCode = :itemCode)
            and (:productType is null or ii.pk.stock.type.type = :productType)
            """)
    Page<InvoiceHistoryResponseDTO> findInvoiceHistoryByFilter(String dealerCNPJ, LocalDateTime emissionPeriod, String invoiceNumber,
                                                               String productType, String productModel, String itemCode,
                                                               String commercialSeries, String chassisNumber,
                                                               InvoiceOperationType operationType, Pageable pageable);

    @Query("""
            select new stock.hub.api.model.dto.report.BaseReportDTO(
            ii.pk.invoice.pk.dealer.cnpj,
            ii.pk.invoice.pk.dealer.name,
            ii.pk.invoice.pk.dealer.countryDesc,
            ii.pk.invoice.pk.dealer.stateDesc,
            ii.pk.invoice.pk.dealer.cityDesc,
            ii.pk.stock.chassisNumber,
            ii.pk.stock.model,
            ii.pk.stock.commercialSeries,
            ii.pk.stock.itemCode,
            ii.pk.stock.type.type,
            ii.pk.stock.type.translationPT,
            ii.pk.stock.type.translationEN,
            ii.pk.stock.type.translationES,
            ii.pk.invoice.pk.number,
            ii.pk.invoice.pk.series,
            ii.pk.invoice.pk.operationType,
            ii.pk.invoice.emissionDate,
            ii.pk.invoice.currency,
            ii.pk.invoice.value,
            ii.pk.invoice.customerDocumentType,
            ii.pk.invoice.customerLegalNumber,
            ii.pk.invoice.customerName,
            ii.pk.invoice.customerCountry,
            ii.pk.invoice.customerState,
            ii.pk.invoice.customerCity,
            ii.pk.invoice.customerAddress,
            ii.pk.invoice.customerAddressComplement,
            ii.pk.invoice.dealerToTransfer)
            from Item ii
            left join ii.pk.invoice.dealerToTransfer as dealerToTransfer
            where ii.pk.invoice.pk.dealer.cnpj = :dealerCNPJ
            and ii.pk.invoice.emissionDate > :emissionPeriod
            and (:invoiceNumber is null or ii.pk.invoice.pk.number = :invoiceNumber)
            and (:operationType is null or ii.pk.invoice.pk.operationType = :operationType)
            and (:chassisNumber is null or ii.pk.stock.chassisNumber = :chassisNumber)
            and (:commercialSeries is null or ii.pk.stock.commercialSeries = :commercialSeries)
            and (:productModel is null or ii.pk.stock.model = :productModel)
            and (:itemCode is null or ii.pk.stock.itemCode = :itemCode)
            and (:productType is null or ii.pk.stock.type.type = :productType)
            """)
    List<BaseReportDTO> findInvoiceHistoryReport(String dealerCNPJ, LocalDateTime emissionPeriod, String invoiceNumber,
                                                 String productType, String productModel, String itemCode,
                                                 String commercialSeries, String chassisNumber, InvoiceOperationType operationType);

    @Query("""
            from Item ii
            where ii.pk.invoice.pk.dealer.cnpj = :dealerCNPJ
            and ii.pk.stock.chassisNumber in :chassisNumberList
            and ii.pk.invoice.pk.operationType = stock.hub.api.model.type.InvoiceOperationType.SALE
            order by ii.pk.invoice.emissionDate desc
            """)
    List<Item> findLastSalesInvoicesByDealerAndChassisList(String dealerCNPJ, List<String> chassisNumberList);

    @Query("""
            from Item ii
            where ii.pk.stock.chassisNumber = :chassisNumber
            order by ii.pk.invoice.emissionDate desc
            """)
    List<Item> findAllInvoiceItemByChassisNumberOrderByEmissionDateDesc(String chassisNumber);

    @Query("""
            select ii
            from Item ii
            where ii.pk.stock.chassisNumber = :chassisNumber
            and ii.pk.invoice.pk.dealer.cnpj = :dealerCNPJ
            and ii.pk.invoice.pk.number = :invoiceNumber
            and ii.pk.invoice.pk.series = :invoiceSeries
            and ii.pk.invoice.pk.operationType = :operationType
            """)
    Item findItemByPK(String chassisNumber, String dealerCNPJ, String invoiceNumber, String invoiceSeries, InvoiceOperationType operationType);

    @Query("""
            select ii
            from Item ii
            where ii.pk.invoice.pk.dealer.cnpj = :dealerCNPJ
            and ii.pk.invoice.pk.number = :invoiceNumber
            and ii.pk.invoice.pk.series = :invoiceSeries
            and ii.pk.invoice.pk.operationType = :operationType
            """)
    List<Item> findItemsByInvoice(String dealerCNPJ, String invoiceNumber, String invoiceSeries, InvoiceOperationType operationType);

}
