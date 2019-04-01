package ar.edu.unq.grupol.app.webservice.exception;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import org.slf4j.Logger;
import lombok.Getter;

public abstract class RestException extends Exception {

	private static final long serialVersionUID = 6375582907977820802L;

	@Getter
	protected Logger logger;
	
	@Context 
	private ResourceInfo resourceInfo;

	public RestException(String message, Logger logger) {
		super(message);
		this.logger = logger;
	}
	
}