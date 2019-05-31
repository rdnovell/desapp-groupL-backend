package ar.edu.unq.groupl.app.model;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.service.dto.LoginDTO;
import org.apache.commons.validator.routines.EmailValidator;

public class Validator {

	public static void validateUser(User user) throws InvalidParameterException {
		validateConstraints(user);
		EmailValidator.getInstance().isValid(user.getEmail());
	}
	
	public static void validateLoginDTO(LoginDTO loginDTO) throws InvalidParameterException {
		validateConstraints(loginDTO);
	}
	
	public static void validateEvent(Event event) throws InvalidParameterException {
		validateConstraints(event);
	}

    private static <T> void validateConstraints(T object) throws InvalidParameterException {
        Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if(!violations.isEmpty()) {
            throw new InvalidParameterException(violations.stream().findFirst().get().getMessage());
        }
    }
	
}