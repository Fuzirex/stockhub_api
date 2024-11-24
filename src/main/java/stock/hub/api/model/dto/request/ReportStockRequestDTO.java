package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStockRequestDTO {

    @NotBlank(message = "{msg.exceptions.001}")
    private String dealerCNPJ;
    private String productType;
    private String model;
    private String itemCode;
    private String commercialSeries;
    private String chassisNumber;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
