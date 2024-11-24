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
    MANDATORY_FIELDS_NOT_INFORMED("msg.exceptions.004"),
    CHASSIS_NUMBER_NOT_INFORMED("msg.exceptions.005"),
    OPERATION_TYPE_NOT_INFORMED("msg.exceptions.006"),
    INVOICE_INFO_NOT_INFORMED("msg.exceptions.008"),
    PRODUCT_NOT_FOUND("msg.exceptions.011"),
    CUSTOMER_INFO_NOT_INFORMED("msg.exceptions.014"),

    // Invalids
    INVALID_USER_OR_PASSWORD("msg.exceptions.003"),
    INVALID_DEALER("msg.exceptions.007"),
    INVALID_DEALER_TO_TRANSFER("msg.exceptions.009"),
    INVALID_STOCK_STATUS("msg.exceptions.012"),

    // Not Found
    CUSTOMER_COUNTRY_NOT_FOUND("msg.exceptions.015"),
    CUSTOMER_STATE_NOT_FOUND("msg.exceptions.016"),
    CUSTOMER_CITY_NOT_FOUND("msg.exceptions.017"),
    LAST_SALE_INVOICE_NOT_FOUND("msg.exceptions.018"),

    // Others
    EMISSION_DATE_AFTER_NOW("msg.exceptions.010"),
    INVOICE_ALREADY_SUBMITTED("msg.exceptions.013"),
    PRODUCTS_FROM_DIF_INVOICES("msg.exceptions.019"),
    RETURN_DATE_BEFORE_LAST_SALE("msg.exceptions.020"),
    MORE_RECENT_INVOICE_ALREADY_EXISTS("msg.exceptions.021");

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
