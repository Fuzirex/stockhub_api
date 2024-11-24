package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportInvoiceHistoryRequestDTO {

    @NotBlank(message = "{msg.exceptions.001}")
    private String dealerCNPJ;
    private String invoiceNumber;
    private Integer operationType;
    @NotNull(message = "{msg.exceptions.004}")
    private LocalDateTime emissionPeriod;
    private String productType;
    private String productModel;
    private String itemCode;
    private String commercialSeries;
    private String chassisNumber;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
