package stock.hub.api.model.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import stock.hub.api.model.type.ExceptionType;

import javax.naming.AuthenticationException;

@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"stackTrace", "suppressed"})
public class StockHubException extends RuntimeException {

    private final Integer code;
    private final String errorMessage;

    public StockHubException(final HttpStatus status, final AuthenticationException exception) {
        this.code = status.value();
        this.errorMessage = exception.getMessage();
    }

    public StockHubException(final HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorMessage = httpStatus.getReasonPhrase();
    }

    public StockHubException(final String errorMessage) {
        this.code = null;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final HttpStatus httpStatus, final String errorMessage) {
        this.code = httpStatus.value();
        this.errorMessage = errorMessage;
    }

    public StockHubException(final Integer code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final String message, final Integer code, final String errorMessage) {
        super(message);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final String message, final Throwable cause, final Integer code, final String errorMessage) {
        super(message, cause);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final Throwable cause, final Integer code, final String errorMessage) {
        super(cause);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final String message, final Throwable cause, final boolean enableSuppression,
                             final boolean writableStackTrace, final Integer code, final String errorMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public StockHubException(final ExceptionType exceptionType) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.errorMessage = exceptionType.getMessage();
    }

}
