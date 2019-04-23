package ar.edu.unq.grupol.app.model.exception;

public abstract class EventException extends Exception {

	private static final long serialVersionUID = 1362892161487763736L;

	public EventException(String message) {
		super(message);
	}

}
