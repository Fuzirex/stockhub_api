package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }

}
