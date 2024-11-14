package stock.hub.api.configuration.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.ExceptionType;

import java.util.Objects;
import java.util.function.Consumer;

@RestController
@ControllerAdvice
public class ExceptionHandlerConfiguration {

    @ExceptionHandler(value = StockHubException.class)
    public ResponseEntity<Object> handleException(StockHubException e) {
        return ResponseEntity.status(e.getCode()).headers(createHeaders()).body(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<Object> handleServletRequestException(ServletRequestBindingException e) {
        return ResponseEntity.badRequest()
                .headers(createHeaders())
                .body(new StockHubException(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest()
                .headers(createHeaders())
                .body(new StockHubException(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();

        return ResponseEntity.badRequest()
                .headers(createHeaders())
                .body(new StockHubException(HttpStatus.BAD_REQUEST, message));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        StockHubException exc = new StockHubException(ExceptionType.INVALID_USER_OR_PASSWORD);
        return ResponseEntity.status(exc.getCode()).headers(createHeaders()).body(exc);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        String message = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage()
                : Objects.nonNull(e.getCause()) ? e.getCause().getMessage() : null;

        return ResponseEntity.internalServerError()
                .headers(createHeaders())
                .body(new StockHubException(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }

    private Consumer<HttpHeaders> createHeaders() {
        return headers -> headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
