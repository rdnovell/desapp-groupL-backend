package ar.edu.unq.grupol.app.webservice.endpoint;

import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.unq.grupol.app.webservice.exception.BadRequestException;
import ar.edu.unq.grupol.app.webservice.exception.NotFoundException;
import ar.edu.unq.grupol.app.webservice.util.RestUtils;

public class Rest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Response ok() {
		return RestUtils.ok();
	}
	
	protected <T> Response ok(T object) {
		return RestUtils.ok(object);
	}
	
	protected void badRequest(String message) throws BadRequestException {
		throw new BadRequestException(message, logger);
	}
	
	protected void notFound(String message) throws NotFoundException {
		throw new NotFoundException(message, logger);
	}
	
}