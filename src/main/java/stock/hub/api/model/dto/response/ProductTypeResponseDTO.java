package stock.hub.api.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTypeResponseDTO {

    private String id;
    private String description;

}
