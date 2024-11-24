package stock.hub.api.model.dto.report;

import lombok.*;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.util.ProductTypeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseReportDTO {

    // Dealer Info
    private String dealerCNPJ;
    private String dealerName;
    private String dealerCountry;
    private String dealerState;
    private String dealerCity;

    // Stock Info
    private String productType;
    private String productChassisNumber;
    private String productCommercialSeries;
    private String productModel;
    private String productItemCode;

    // Invoice Info
    private String invoiceNumber;
    private String invoiceSeries;
    private InvoiceOperationType invoiceOperationType;
    private LocalDateTime invoiceEmissionDate;
    private BigDecimal invoiceValue;
    private CurrencyType invoiceCurrency;
    private DocumentType invoiceCustomerDocumentType;
    private String invoiceCustomerLegalNumber;
    private String invoiceCustomerName;
    private String invoiceCustomerCountry;
    private String invoiceCustomerState;
    private String invoiceCustomerCity;

    private String invoiceDealerToTransfer;

    // Stock Report Constructor
    public BaseReportDTO(String dealerCNPJ, String dealerName, String dealerCountry, String dealerState, String dealerCity,
                         String productChassisNumber, String productModel, String productCommercialSeries, String productItemCode, String productType,
                         String productTypeTranslationPT, String productTypeTranslationEN, String productTypeTranslationES) {
        this.dealerCNPJ = dealerCNPJ;
        this.dealerName = dealerName;
        this.dealerCountry = dealerCountry;
        this.dealerState = dealerState;
        this.dealerCity = dealerCity;
        this.productChassisNumber = productChassisNumber;
        this.productModel = productModel;
        this.productCommercialSeries = productCommercialSeries;
        this.productItemCode = productItemCode;
        this.productType = String.format("%s - %s", productType, ProductTypeUtils.getProductTypeTranslation(productTypeTranslationPT, productTypeTranslationEN, productTypeTranslationES));
    }

}
