package stock.hub.api.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import stock.hub.api.model.type.InvoiceOperationType;

import java.util.Objects;

@Convert
public class InvoiceOperationTypeConverter implements AttributeConverter<InvoiceOperationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InvoiceOperationType attribute) {
        return Objects.nonNull(attribute) ? attribute.getId() : null;
    }

    @Override
    public InvoiceOperationType convertToEntityAttribute(Integer dbData) {
        return Objects.nonNull(dbData) ? InvoiceOperationType.getInvoiceOperationType(dbData) : null;
    }
}
