package ar.edu.unq.groupl.app.service.dto;

import java.util.List;

import ar.edu.unq.groupl.app.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketDTO extends EventDTO {
	public BasketDTO(Event event) {
		super(event);
	}

	private List<ItemAssignedDTO> itemsAssigned;
}