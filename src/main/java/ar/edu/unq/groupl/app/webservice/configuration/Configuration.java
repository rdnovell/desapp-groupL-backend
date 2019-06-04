package ar.edu.unq.groupl.app.webservice.configuration;

import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import org.glassfish.jersey.server.ResourceConfig;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.webservice.exceptionhandler.BadRequestExceptionHandler;
import ar.edu.unq.groupl.app.webservice.exceptionhandler.InvalidParameterExceptionHandler;
import ar.edu.unq.groupl.app.webservice.exceptionhandler.NotFoundExceptionHandler;
import ar.edu.unq.groupl.app.webservice.exceptionhandler.ServerExceptionHandler;
import ar.edu.unq.groupl.app.webservice.exceptionhandler.UnexistExceptionHandler;

@Component
@ApplicationPath("/api")
public class Configuration extends ResourceConfig {
	
	public Configuration() {
		scan("ar.edu.unq.groupl.app.webservice.endpoint");
	}
	
	private void scan(String... packages) {
		Stream.of(packages).forEach(pack -> registerPackage(pack));
	}
	
	private void registerPackage(String pack) {
		Reflections reflections = new Reflections(pack);
		reflections.getTypesAnnotatedWith(Path.class).stream().forEach(clazz -> register(clazz));
		register(BadRequestExceptionHandler.class);
		register(ServerExceptionHandler.class);
		register(NotFoundExceptionHandler.class);
		register(InvalidParameterExceptionHandler.class);
		register(UnexistExceptionHandler.class);
	}

}