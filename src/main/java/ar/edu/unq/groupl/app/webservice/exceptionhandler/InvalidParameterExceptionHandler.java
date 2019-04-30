package ar.edu.unq.groupl.app.webservice.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.webservice.util.RestUtils;

@Provider
public class InvalidParameterExceptionHandler extends RestExceptionHandler implements ExceptionMapper<InvalidParameterException> {

	@Override
	public Response toResponse(InvalidParameterException exception) {
		return RestUtils.badRequest(exception.getMessage());
	}

}