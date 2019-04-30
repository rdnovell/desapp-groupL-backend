package ar.edu.unq.groupl.app.webservice.exception;

import org.slf4j.Logger;

public class BadRequestException extends RestException {

	private static final long serialVersionUID = 1958205654023933995L;

	public BadRequestException(String message, Logger logger) {
		super(message, logger);
	}
	
}