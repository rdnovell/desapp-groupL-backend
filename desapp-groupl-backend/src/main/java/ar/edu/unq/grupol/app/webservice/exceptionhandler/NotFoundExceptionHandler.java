package ar.edu.unq.grupol.app.webservice.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import ar.edu.unq.grupol.app.webservice.exception.NotFoundException;
import ar.edu.unq.grupol.app.webservice.util.RestUtils;

@Provider
public class NotFoundExceptionHandler extends RestExceptionHandler implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException notFoundException) {
		logError(notFoundException.getLogger(), notFoundException);
		return RestUtils.notFound(notFoundException.getMessage());
	}
	
}