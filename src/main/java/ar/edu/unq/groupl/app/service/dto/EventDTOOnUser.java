package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;
import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Item;
import lombok.Getter;

@Getter
public abstract class EventDTOOnUser {

	private Integer id;
	private String title;
	private List<Item> items;
	private LocalDate date;
	
	public EventDTOOnUser(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.items = event.getItems();
		this.date = event.getDate();
	}

}
