package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventDTO {
	private Integer id;
	private String title;
	private String owner;
	private List<String> guests;
	private List<Integer> items;
	private LocalDate date;

	public EventDTO(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.owner = event.getOwner().getEmail();
		//this.guests = ListUtil.toList(event.getGuests().stream().map(User::getEmail));
		this.items = ListUtil.toList(event.getItems().stream().map(Item::getId));
	}
}
