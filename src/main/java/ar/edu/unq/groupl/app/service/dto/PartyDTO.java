package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;

import ar.edu.unq.groupl.app.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PartyDTO extends EventDTO {
	public PartyDTO(Event event) {
		super(event);
	}

	private LocalDate expirationDate;
}