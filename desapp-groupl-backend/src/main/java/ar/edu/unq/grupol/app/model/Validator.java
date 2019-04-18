package ar.edu.unq.grupol.app.model;

import java.util.Set;
import java.util.regex.Pattern;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public class Validator {

	public static void validateUser(User user) throws InvalidParameterException {
		validateConstraints(user);
		validateUserEmail(user);
		validateUserPasword(user);
	}
	
	public static void validateParty(Party party) throws InvalidParameterException {
		validateConstraints(party);
	}

    private static <T> void validateConstraints(T object) throws InvalidParameterException {
        Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if(!violations.isEmpty()) {
            throw new InvalidParameterException(violations.stream().findFirst().get().getMessage());
        }

    }

    private static void validateUserPasword(User user) throws InvalidParameterException {
		final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-zA-Z]).{4,10})";
		
		if (!Pattern.compile(PASSWORD_PATTERN).matcher(user.getPassword()).matches())
			throw new InvalidParameterException("User with invalid password");
	}

	private static void validateUserEmail(User user) throws InvalidParameterException {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{0,})$";
		
		if (!Pattern.compile(EMAIL_PATTERN).matcher(user.getEmail()).matches())
			throw new InvalidParameterException("User with invalid email");
	}
}
