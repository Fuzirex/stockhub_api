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
public class StockRequestDTO {

    @NotBlank(message = "{msg.exceptions.001}")
    private String dealerCNPJ;
    private String productType;
    private String model;
    private String itemCode;
    private String commercialSeries;
    private String chassisNumber;
    @NotNull(message = "{msg.exceptions.004}")
    private Integer page;
    @NotNull(message = "{msg.exceptions.004}")
    private Integer size;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
