package stock.hub.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stock.hub.api.model.dto.response.StockResponseDTO;
import stock.hub.api.model.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    @Query("""
            select new stock.hub.api.model.dto.response.StockResponseDTO(
            s.chassisNumber,
            s.model,
            s.commercialSeries,
            s.itemCode,
            s.dealer.cnpj,
            s.status,
            s.type.type,
            s.type.translationPT,
            s.type.translationEN,
            s.type.translationES)
            from Stock s
            where s.dealer.cnpj = :dealerCNPJ
            and s.status = stock.hub.api.model.type.StockStatusType.AVAILABLE
            and (:chassisNumber is null or s.chassisNumber = :chassisNumber)
            and (:commercialSeries is null or s.commercialSeries = :commercialSeries)
            and (:model is null or s.model = :model)
            and (:itemCode is null or s.itemCode = :itemCode)
            and (:productType is null or s.type.type = :productType)
            """)
    Page<StockResponseDTO> findDealerStockByFilter(String dealerCNPJ, String productType, String model, String itemCode,
                                                   String commercialSeries, String chassisNumber, Pageable pageable);
}
