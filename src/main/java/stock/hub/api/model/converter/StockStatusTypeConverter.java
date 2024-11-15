package stock.hub.api.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import stock.hub.api.model.type.StockStatusType;

import java.util.Objects;

@Convert
public class StockStatusTypeConverter implements AttributeConverter<StockStatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StockStatusType attribute) {
        return Objects.nonNull(attribute) ? attribute.getId() : null;
    }

    @Override
    public StockStatusType convertToEntityAttribute(Integer dbData) {
        return Objects.nonNull(dbData) ? StockStatusType.getStockStatusType(dbData) : null;
    }
}
