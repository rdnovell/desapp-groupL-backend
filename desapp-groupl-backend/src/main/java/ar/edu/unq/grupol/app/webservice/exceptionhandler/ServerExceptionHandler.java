package ar.edu.unq.grupol.app.webservice.exceptionhandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.unq.grupol.app.webservice.util.RestUtils;

@Provider
public class ServerExceptionHandler extends RestExceptionHandler implements ExceptionMapper<Exception> {

	private Logger logger = LoggerFactory.getLogger(ServerExceptionHandler.class);
	
	@Override
	public Response toResponse(Exception exception) {
		logError(logger, exception);
		return RestUtils.internalServerError("Hubo en error al invocar el servicio.");
	}
	
}