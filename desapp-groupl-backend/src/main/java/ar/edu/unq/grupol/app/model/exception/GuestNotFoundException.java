package ar.edu.unq.grupol.app.model.exception;

public class GuestNotFoundException extends EventException {
	
	private static final long serialVersionUID = -1531825702921687279L;

	public GuestNotFoundException(String message) {
		super(message);
	}
	
}
