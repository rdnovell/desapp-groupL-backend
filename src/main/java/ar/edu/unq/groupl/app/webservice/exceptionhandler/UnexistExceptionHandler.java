package ar.edu.unq.groupl.app.webservice.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import ar.edu.unq.groupl.app.webservice.util.RestUtils;

@Provider
public class UnexistExceptionHandler extends RestExceptionHandler implements ExceptionMapper<UnexistException>{

	@Override
	public Response toResponse(UnexistException exception) {
		return RestUtils.notFound(exception.getMessage());
	}
	
}
