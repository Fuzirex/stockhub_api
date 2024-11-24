package stock.hub.api.model.dto.report;

import lombok.*;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.util.ProductTypeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private String invoiceCustomerAddress;
    private String invoiceCustomerAddressComplement;

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

    // Invoice History Report Constructor
    public BaseReportDTO(String dealerCNPJ,
                         String dealerName,
                         String dealerCountry,
                         String dealerState,
                         String dealerCity,
                         String productChassisNumber,
                         String productModel,
                         String productCommercialSeries,
                         String productItemCode,
                         String productType,
                         String productTypeTranslationPT,
                         String productTypeTranslationEN,
                         String productTypeTranslationES,
                         String invoiceNumber,
                         String invoiceSeries,
                         InvoiceOperationType invoiceOperationType,
                         LocalDateTime invoiceEmissionDate,
                         CurrencyType invoiceCurrency,
                         BigDecimal invoiceValue,
                         DocumentType invoiceCustomerDocumentType,
                         String invoiceCustomerLegalNumber,
                         String invoiceCustomerName,
                         String invoiceCustomerCountry,
                         String invoiceCustomerState,
                         String invoiceCustomerCity,
                         String invoiceCustomerAddress,
                         String invoiceCustomerAddressComplement,
                         Dealer dealerToTransfer) {
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

        this.invoiceNumber = invoiceNumber;
        this.invoiceSeries = invoiceSeries;
        this.invoiceOperationType = invoiceOperationType;
        this.invoiceEmissionDate = invoiceEmissionDate;
        this.invoiceCurrency = invoiceCurrency;
        this.invoiceValue = invoiceValue;

        if (invoiceOperationType == InvoiceOperationType.TRANSFER && Objects.nonNull(dealerToTransfer)) {
            this.invoiceCustomerDocumentType = DocumentType.CNPJ;
            this.invoiceCustomerLegalNumber = dealerToTransfer.getCnpj();
            this.invoiceCustomerName = dealerToTransfer.getName();
            this.invoiceCustomerCountry = dealerToTransfer.getCountryDesc();
            this.invoiceCustomerState = dealerToTransfer.getStateDesc();
            this.invoiceCustomerCity = dealerToTransfer.getCityDesc();
        } else {
            this.invoiceCustomerDocumentType = invoiceCustomerDocumentType;
            this.invoiceCustomerLegalNumber = invoiceCustomerLegalNumber;
            this.invoiceCustomerName = invoiceCustomerName;
            this.invoiceCustomerCountry = invoiceCustomerCountry;
            this.invoiceCustomerState = invoiceCustomerState;
            this.invoiceCustomerCity = invoiceCustomerCity;
            this.invoiceCustomerAddress = invoiceCustomerAddress;
            this.invoiceCustomerAddressComplement = invoiceCustomerAddressComplement;
        }
    }

}
