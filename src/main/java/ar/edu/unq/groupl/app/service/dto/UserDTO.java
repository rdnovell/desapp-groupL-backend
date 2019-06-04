package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

	private String name;
	private String lastName;
	private String email;
	private List<EventDTO> eventsAssisted;
	private List<EventDTO> eventsCoursed;
	private List<EventDTO> eventsInCourse;
	private Boolean dutiful;
	
	public UserDTO(User user) {
		name = user.getName();
		lastName = user.getLastName();
		email = user.getEmail();
		eventsAssisted = transformEvents(user.getEventsAssisted());
		eventsCoursed = transformEvents(user.getEventsCoursed());
		eventsInCourse = transformEvents(user.getEventsInCourse());
		dutiful = user.isDutiful();
	}
	
	private List<EventDTO> transformEvents(List<Event> events) {
		return ListUtil.toList(events.stream().map(event -> new EventDTO(event)));
	}
	
}
