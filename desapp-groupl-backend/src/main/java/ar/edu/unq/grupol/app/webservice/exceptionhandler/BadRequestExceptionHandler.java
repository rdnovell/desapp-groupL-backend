package ar.edu.unq.grupol.app.webservice.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import ar.edu.unq.grupol.app.webservice.exception.BadRequestException;
import ar.edu.unq.grupol.app.webservice.util.RestUtils;

@Provider
public class BadRequestExceptionHandler extends RestExceptionHandler implements ExceptionMapper<BadRequestException> {

	@Override
	public Response toResponse(BadRequestException badRequest) {
		logError(badRequest.getLogger(), badRequest);
		return RestUtils.badRequest(badRequest.getMessage());
	}
	
}