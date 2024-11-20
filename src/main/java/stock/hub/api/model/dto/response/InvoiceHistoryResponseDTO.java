package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.util.ObjectMapperUtils;
import stock.hub.api.util.ProductTypeUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceHistoryResponseDTO {

    //Invoice
    private String dealerCNPJ;
    private String invoiceNumber;
    private String invoiceSeries;
    private Integer operationType;
    private String operationTypeDescription;
    private LocalDateTime emissionDate;

    //Product
    private ProductTypeResponseDTO productType;
    private String model;
    private String itemCode;
    private String commercialSeries;
    private String chassisNumber;

    public InvoiceHistoryResponseDTO(final String dealerCNPJ, final String invoiceNumber, final String invoiceSeries,
                                     final InvoiceOperationType operationType, final LocalDateTime emissionDate, final String chassisNumber,
                                     final String commercialSeries, final String itemCode, final String model,
                                     final String productType, String productTypeTranslationPT, String productTypeTranslationEN, String productTypeTranslationES) {
        this.dealerCNPJ = dealerCNPJ;
        this.invoiceNumber = invoiceNumber;
        this.invoiceSeries = invoiceSeries;
        this.operationType = operationType.getId();
        this.operationTypeDescription = operationType.getMessage();
        this.emissionDate = emissionDate;
        this.chassisNumber = chassisNumber;
        this.commercialSeries = commercialSeries;
        this.itemCode = itemCode;
        this.model = model;

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
