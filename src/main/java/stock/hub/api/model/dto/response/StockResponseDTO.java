package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.model.type.StockStatusType;
import stock.hub.api.util.ObjectMapperUtils;
import stock.hub.api.util.ProductTypeUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResponseDTO {

    private String chassisNumber;
    private String model;
    private String commercialSeries;
    private String itemCode;
    private String dealerCNPJ;
    private Integer status;
    private ProductTypeResponseDTO productType;

    public StockResponseDTO(String chassisNumber, String model, String commercialSeries, String itemCode, String dealerCNPJ, StockStatusType status, String productType, String productTypeTranslationPT, String productTypeTranslationEN, String productTypeTranslationES) {
        this.chassisNumber = chassisNumber;
        this.model = model;
        this.commercialSeries = commercialSeries;
        this.itemCode = itemCode;
        this.dealerCNPJ = dealerCNPJ;
        this.status = status.getId();

        this.productType = ProductTypeResponseDTO.builder()
                .id(productType)
                .description(ProductTypeUtils.getProductTypeTranslation(productTypeTranslationPT, productTypeTranslationEN, productTypeTranslationES))
                .build();
    }

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
