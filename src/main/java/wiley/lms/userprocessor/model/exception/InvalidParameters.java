package wiley.lms.userprocessor.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidParameters extends RuntimeException {

    public InvalidParameters(String message) {
        super(message);
    }
}
