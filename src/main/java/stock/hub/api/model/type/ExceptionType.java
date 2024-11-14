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
public enum ExceptionType {

    // Not informed
    CNPJ_NOT_INFORMED("msg.exceptions.001"),
    PASSWORD_NOT_INFORMED("msg.exceptions.002"),

    // Invalids
    INVALID_USER_OR_PASSWORD("msg.exceptions.003");

    // injected by MessageSourceInjector
    @Setter
    private MessageSource messageSource;

    private final String messageCode;

    ExceptionType(final String messageCode) {
        this.messageCode = messageCode;
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
        return messageSource.getMessage(getMessageCode(), null, LocaleContextHolder.getLocale());
    }

}
