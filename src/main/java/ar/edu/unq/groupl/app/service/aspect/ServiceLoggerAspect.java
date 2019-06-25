package ar.edu.unq.groupl.app.service.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Component
public class ServiceLoggerAspect {
	
	@SuppressWarnings("unchecked")
	@Around(value = "@annotation(ar.edu.unq.groupl.app.service.annotation.Log)")
	public <T> T logAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = Logger.getLogger(joinPoint.getTarget().getClass().getSimpleName());
		try {
			T toReturn = (T) joinPoint.proceed();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String parameters = Arrays.stream(joinPoint.getArgs()).map(a -> "Type: " + a.getClass().getSimpleName() + " Value: " + a ).collect(Collectors.joining());
			logger.info("Method " + joinPoint.getSignature().getName() + " executed correctly with parameters [" + parameters + "].");
			return toReturn;
		} catch (Throwable throwable) {
			 Function<String, String> exceptionMessage = message -> message == null ? "." : " - Exception message: " + message;
			 logger.error("Error on method: " + joinPoint.getSignature().getName() + exceptionMessage.apply(throwable.getMessage()));
			 throw throwable;
		}
	}
	
}
