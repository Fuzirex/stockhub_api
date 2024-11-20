package stock.hub.api.model.type;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Getter
@AllArgsConstructor
public enum InvoiceOperationType {

    SALE(1, "invoice-operation-type.sale"),
    RETURN(2, "invoice-operation-type.return"),
    TRANSFER(3, "invoice-operation-type.transfer");

    @Setter
    private MessageSource messageSource;

    private final Integer id;
    private final String translationCode;

    InvoiceOperationType(final Integer id, final String translationCode) {
        this.id = id;
        this.translationCode = translationCode;
    }

    @Component
    public static class MessageSourceInjector {

        private final MessageSource messageSource;

        @Autowired
        MessageSourceInjector(final MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        @PostConstruct
        public void postConstruct() {
            EnumSet.allOf(InvoiceOperationType.class).forEach(type -> type.setMessageSource(messageSource));
        }
    }

    public String getMessage() {
        return messageSource.getMessage(getTranslationCode(), null, LocaleContextHolder.getLocale());
    }

    public static InvoiceOperationType getInvoiceOperationType(Integer id) {
        for (InvoiceOperationType type : EnumSet.allOf(InvoiceOperationType.class))
            if (type.getId().equals(id)) return type;

        throw new EnumConstantNotPresentException(InvoiceOperationType.class, "Invalid Invoice Operation Type, id: " + id);
    }

}
