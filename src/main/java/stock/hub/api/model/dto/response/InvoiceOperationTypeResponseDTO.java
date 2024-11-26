package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.model.type.InvoiceOperationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceOperationTypeResponseDTO {

    private InvoiceOperationType operation;
    private String description;

    public InvoiceOperationTypeResponseDTO(InvoiceOperationType type) {
        this.operation = type;
        this.description = type.getMessage();
    }
}
