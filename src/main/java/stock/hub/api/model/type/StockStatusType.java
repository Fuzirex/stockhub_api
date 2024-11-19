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
public enum StockStatusType {

    AVAILABLE(1, "stock-status.available"),
    SOLD(2, "stock-status.sold");

    @Setter
    private MessageSource messageSource;

    private final Integer id;
    private final String translationCode;

    StockStatusType(final Integer id, final String translationCode) {
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
            EnumSet.allOf(ExceptionType.class).forEach(type -> type.setMessageSource(messageSource));
        }
    }

    public String getMessage() {
        return messageSource.getMessage(getTranslationCode(), null, LocaleContextHolder.getLocale());
    }

    public static StockStatusType getStockStatusType(Integer id) {
        for (StockStatusType type : EnumSet.allOf(StockStatusType.class))
            if (type.getId().equals(id)) return type;

        throw new EnumConstantNotPresentException(StockStatusType.class, "Invalid Stock Status Type, id: " + id);
    }

}
