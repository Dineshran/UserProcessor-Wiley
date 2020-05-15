package wiley.lms.userprocessor.util;

import org.apache.commons.validator.EmailValidator;
import wiley.lms.userprocessor.model.exception.InvalidEmailException;
import wiley.lms.userprocessor.model.exception.InvalidParameters;
import wiley.lms.userprocessor.model.entity.Role;

public class Validator {

    private Validator() {
    }

    public static boolean validateEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            return true;
        } else {
            throw new InvalidEmailException("Email You Provided is Not Valid");
        }
    }

    public static long stringToLong(String longValue) {
        try {
            return Long.parseLong(longValue);
        } catch (NumberFormatException ex) {
            throw new InvalidParameters("must be a long value : " + longValue);
        }
    }

    public static boolean stringToBoolean(String boolValue) {
        if (Boolean.parseBoolean(boolValue)) {
            return true;
        } else if ("false".equalsIgnoreCase(boolValue)) {
            return false;
        } else {
            throw new InvalidParameters("Boolean value is invalid : " + boolValue);
        }
    }

    public static Role stringToRole(String code) {
        return Role.of(code);
    }
}
