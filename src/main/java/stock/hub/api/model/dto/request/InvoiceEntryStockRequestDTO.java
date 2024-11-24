package stock.hub.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.model.type.DocumentType;
import stock.hub.api.util.ObjectMapperUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntryStockRequestDTO {

    private String model;
    private String itemCode;
    private String commercialSeries;
    @NotBlank(message = "{msg.exceptions.005}")
    private String chassisNumber;

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
