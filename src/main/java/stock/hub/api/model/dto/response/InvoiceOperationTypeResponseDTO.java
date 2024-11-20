package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.model.type.InvoiceOperationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceOperationTypeResponseDTO {

    private Integer id;
    private String description;

    public InvoiceOperationTypeResponseDTO(InvoiceOperationType type) {
        this.id = type.getId();
        this.description = type.getMessage();
    }
}
