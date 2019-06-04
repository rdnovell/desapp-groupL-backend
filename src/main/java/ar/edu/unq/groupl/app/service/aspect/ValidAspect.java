package ar.edu.unq.groupl.app.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;

@Aspect
@Component
public class ValidAspect {

	@SuppressWarnings("unchecked")
	@Around(value = "@annotation(ar.edu.unq.groupl.app.service.annotation.Valid)")
	public <T> T valid(ProceedingJoinPoint joinPoint) throws Throwable {
		T objectToValidate = (T) joinPoint.getArgs()[0];
		Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(objectToValidate);
        if(!violations.isEmpty()) {
            throw new InvalidParameterException(violations.stream().findFirst().get().getMessage());
        }
	    return (T) joinPoint.proceed();
	}
	
}
