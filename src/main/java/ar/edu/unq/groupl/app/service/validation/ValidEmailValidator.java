package ar.edu.unq.groupl.app.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailValidator implements ConstraintValidator<Email, String>{

	@Override
	public boolean isValid(final String value, ConstraintValidatorContext context) {
		return value != null && EmailValidator.getInstance().isValid(value);
	}

}
