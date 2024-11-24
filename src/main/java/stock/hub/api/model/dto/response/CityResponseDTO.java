package stock.hub.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityResponseDTO {

    @JsonAlias("municipio-id")
    private Long code;
    @JsonAlias("municipio-nome")
    private String description;
    @JsonAlias("UF-id")
    private String stateID;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
