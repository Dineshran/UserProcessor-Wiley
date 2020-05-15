package wiley.lms.userprocessor.model.exception;

import lombok.NoArgsConstructor;

public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException(String message) {
        super(message);
    }
}
