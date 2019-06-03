package ar.edu.unq.groupl.app.service.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class EventUsersDTO {
	
	private Integer eventId;
	private List<String> userEmails;
	
}
