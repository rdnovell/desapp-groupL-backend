package ar.edu.unq.groupl.app.model.exception;

public class InvitationExpiredException extends EventException {
	
	private static final long serialVersionUID = -4107801316443169005L;

	public InvitationExpiredException(String message) {
		super(message);
	}
	
}