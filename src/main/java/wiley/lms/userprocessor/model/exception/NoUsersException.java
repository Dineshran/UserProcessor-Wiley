package wiley.lms.userprocessor.model.exception;

import lombok.NoArgsConstructor;

public class NoUsersException extends RuntimeException{

    public NoUsersException(String message) {
        super(message);
    }
}
