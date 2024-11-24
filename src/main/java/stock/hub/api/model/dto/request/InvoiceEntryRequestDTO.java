package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.util.ObjectMapperUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntryRequestDTO {

    @NotBlank(message = "{msg.exceptions.008}")
    private String invoiceNumber;
    @NotBlank(message = "{msg.exceptions.008}")
    private String invoiceSeries;
    @NotNull(message = "{msg.exceptions.008}")
    private LocalDateTime emissionDate;
    private BigDecimal invoiceValue;
    private CurrencyType currencyType;

    private DocumentType customerDocumentType;
    private String customerLegalNumber;
    private String customerName;
    private Long customerCountry;
    private Long customerState;
    private Long customerCity;
    private String customerAddress;
    private String customerComplement;

    @NotNull(message = "{msg.exceptions.006}")
    private InvoiceOperationType operationType;

    @NotBlank(message = "{msg.exceptions.001}")
    private String dealerCNPJ;
    private String dealerToTransfer;

    @NotEmpty(message = "{msg.exceptions.004}")
    private List<InvoiceEntryStockRequestDTO> products;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
