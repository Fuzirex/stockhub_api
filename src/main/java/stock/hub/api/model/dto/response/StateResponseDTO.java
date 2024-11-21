package stock.hub.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResponseDTO {

    @JsonAlias("UF-id")
    private Long code;
    @JsonAlias("UF-nome")
    private String description;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
