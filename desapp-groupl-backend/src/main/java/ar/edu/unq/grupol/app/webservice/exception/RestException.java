package ar.edu.unq.grupol.app.webservice.exception;

import org.slf4j.Logger;
import lombok.Getter;

public abstract class RestException extends Exception {

	private static final long serialVersionUID = 6375582907977820802L;

	@Getter
	protected Logger logger;

	public RestException(String message, Logger logger) {
		super(message);
		this.logger = logger;
	}
	
}