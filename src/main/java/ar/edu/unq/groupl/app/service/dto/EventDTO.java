package ar.edu.unq.groupl.app.service.dto;

import ar.edu.unq.groupl.app.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class EventDTO {

	private Integer id;
	private String title;
	
	public EventDTO(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
	}
	
}
