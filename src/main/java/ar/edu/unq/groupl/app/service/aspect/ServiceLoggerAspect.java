package ar.edu.unq.groupl.app.service.aspect;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Aspect
@Component
public class ServiceLoggerAspect {
	
	@SuppressWarnings("unchecked")
	@Around(value = "@annotation(ar.edu.unq.groupl.app.service.annotation.Log)")
	public <T> T logAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		Object token = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        DecodedJWT decode = JWT.decode(token.toString());
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass().getSimpleName());
		try {
			T toReturn = (T) joinPoint.proceed();
			logger.info("Method " + joinPoint.getSignature().getName() + " executed correctly by user " + decode.getClaim("email").asString() + ".");
			return toReturn;
		} catch (Throwable throwable) {
			 Function<String, String> exceptionMessage = message -> message == null ? "." : " - Exception message: " + message;
			 logger.error("Error on method: " + joinPoint.getSignature().getName() + exceptionMessage.apply(throwable.getMessage()));
			 throw throwable;
		}
	}
	
}
