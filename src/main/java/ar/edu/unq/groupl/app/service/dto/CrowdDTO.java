package ar.edu.unq.groupl.app.service.dto;

import ar.edu.unq.groupl.app.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrowdDTO extends EventDTO {
	public CrowdDTO(Event event) {
		super(event);
	}

}