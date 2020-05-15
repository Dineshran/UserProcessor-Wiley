package wiley.lms.userprocessor.util;

import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wiley.lms.userprocessor.model.entity.Role;
import wiley.lms.userprocessor.model.exception.InvalidEmailException;
import wiley.lms.userprocessor.model.exception.InvalidParametersException;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private Validator() {
    }

    public static boolean validateEmail(String email) {
        logger.debug("Validating Email Address : " + email);
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            logger.debug("Valid Email Address : " + email);
            return true;
        } else {
            logger.error("InValid Email Address : " + email);
            throw new InvalidEmailException("Email You Provided is Not Valid");
        }
    }

    public static long stringToLong(String longValue) {
        logger.debug("Converting to long value : " + longValue);
        try {
            logger.debug("Converted to long value : " + longValue);
            return Long.parseLong(longValue);
        } catch (NumberFormatException ex) {
            logger.debug("Not a valid long value : " + longValue);
            throw new InvalidParametersException("must be a long value : " + longValue);
        }
    }

    public static boolean stringToBoolean(String boolValue) {
        logger.debug("Converting to boolean value : " + boolValue);
        if (Boolean.parseBoolean(boolValue)) {
            logger.debug("Converted to boolean value : " + true);
            return true;
        } else if ("false".equalsIgnoreCase(boolValue)) {
            logger.debug("Converted to boolean value : " + false);
            return false;
        } else {
            logger.debug("Invalid boolean value : " + boolValue);
            throw new InvalidParametersException("Boolean value is invalid : " + boolValue);
        }
    }

    public static Role stringToRole(String code) {
        logger.debug("Get Enum for : " + code);
        return Role.of(code);
    }
}
