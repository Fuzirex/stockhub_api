package stock.hub.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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
            and (:longItemCode is null or ii.pk.stock.itemCode = :longItemCode)
            and (:productType is null or ii.pk.stock.type.type = :productType)
            """)
    Page<InvoiceHistoryResponseDTO> findInvoiceHistoryByFilter(String dealerCNPJ, LocalDateTime emissionPeriod, String invoiceNumber,
                                                               String productType, String productModel, String longItemCode,
                                                               String commercialSeries, String chassisNumber,
                                                               InvoiceOperationType operationType, Pageable pageable);

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

}
