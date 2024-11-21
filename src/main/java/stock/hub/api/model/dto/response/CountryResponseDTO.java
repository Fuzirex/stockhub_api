package stock.hub.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryResponseDTO {

    @JsonAlias("pais-M49")
    private Long code;
    @JsonAlias("pais-nome")
    private String description;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
