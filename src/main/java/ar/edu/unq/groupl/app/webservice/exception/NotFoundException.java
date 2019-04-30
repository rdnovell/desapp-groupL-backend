package ar.edu.unq.groupl.app.webservice.exception;

import org.slf4j.Logger;

public class NotFoundException extends RestException {

	private static final long serialVersionUID = -981563679770941032L;

	public NotFoundException(String message, Logger logger) {
		super(message, logger);
	}
	
}