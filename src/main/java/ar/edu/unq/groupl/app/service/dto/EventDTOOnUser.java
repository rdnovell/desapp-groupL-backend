package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Item;
import lombok.Getter;

@Getter
public abstract class EventDTOOnUser {

	private Integer id;
	private String title;
	private List<Item> items;
	private List<String> guests;
	private LocalDate date;
	
	public EventDTOOnUser(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.items = event.getItems();
		this.guests = event.getGuests().stream().map(e -> e.getEmail()).collect(Collectors.toList());
		this.date = event.getDate();
	}

}
