package wiley.lms.userprocessor.controller.error;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wiley.lms.userprocessor.model.exception.*;

import java.util.Date;

@RestControllerAdvice
@RestController
public class UserExceptionController extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(UserExceptionController.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        logger.debug("Entered handleAllExceptions with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        logger.debug("Entered handleAllExceptions with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug("Entered handleMethodArgumentNotValid with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidEmailException(InvalidEmailException ex, WebRequest request) {
        logger.debug("Entered handleInvalidEmailException with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidParametersException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidParametersException(InvalidParametersException ex, WebRequest request) {
        logger.debug("Entered handleInvalidParametersException with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidRoleException(InvalidRoleException ex, WebRequest request) {
        logger.debug("Entered handleInvalidRoleException with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoUsersException.class)
    public final ResponseEntity<ErrorDetails> handleNoUsersException(NoUsersException ex, WebRequest request) {
        logger.debug("Entered handleNoUsersException with : " + ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
}

