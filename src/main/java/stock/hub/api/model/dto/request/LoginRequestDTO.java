package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "{msg.exceptions.001}")
    private String cnpj;
    @NotBlank(message = "{msg.exceptions.002}")
    private String password;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }

}
