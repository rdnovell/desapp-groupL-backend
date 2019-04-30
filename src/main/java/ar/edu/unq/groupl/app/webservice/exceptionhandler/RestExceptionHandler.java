package ar.edu.unq.groupl.app.webservice.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import org.slf4j.Logger;
import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class RestExceptionHandler {
	
	@Context 
	protected ResourceInfo resourceInfo;
	
	protected String getMethodThatThrewException() {
		return resourceInfo.getResourceMethod().getName();
	}
	
	protected String getParamethersOfTheMethod() {
		Paranamer paranamer = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
	    String[] parameterNames = paranamer.lookupParameterNames(resourceInfo.getResourceMethod());
	    List<String> classNames = Stream.of(resourceInfo.getResourceMethod().getParameterTypes()).map(clase -> clase.getName()).collect(Collectors.toList());
	    return IntStream.range(0, parameterNames.length)
	    	.mapToObj(index -> classNames.get(index) + " " + parameterNames[index])
	    	.collect(Collectors.joining(", ", "(", ")"));
	}
	
	protected void logError(Logger logger, Exception exception) {
		logger.error("En el método " + getMethodThatThrewException() + getParamethersOfTheMethod(), exception);
	}
	
}