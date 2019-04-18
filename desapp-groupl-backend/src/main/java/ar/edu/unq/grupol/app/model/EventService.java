package ar.edu.unq.grupol.app.model;

public class EventService {

	public Event createEvent(Event event) {
		event.sendInvitations();
		return event;
		
	}
}
