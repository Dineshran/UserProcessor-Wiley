package wiley.lms.userprocessor.model.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message){
        super(message);
    }
}
