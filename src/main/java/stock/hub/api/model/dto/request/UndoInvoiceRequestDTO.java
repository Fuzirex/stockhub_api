package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UndoInvoiceRequestDTO {

    @NotBlank(message = "{msg.exceptions.004}")
    private String dealerCNPJ;

    @NotBlank(message = "{msg.exceptions.004}")
    private String invoiceNumber;

    @NotBlank(message = "{msg.exceptions.004}")
    private String invoiceSeries;

    @NotNull(message = "{msg.exceptions.004}")
    private InvoiceOperationType operationType;

    @NotBlank(message = "{msg.exceptions.004}")
    private String chassisNumber;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
