package stock.hub.api.model.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvalidFieldExceptionDTO {

    String field;
    String message;

    public InvalidFieldExceptionDTO(FieldError error) {
        field = error.getField();
        message = error.getDefaultMessage();
    }
}
