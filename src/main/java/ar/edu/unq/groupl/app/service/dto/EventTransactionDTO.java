package ar.edu.unq.groupl.app.service.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import ar.edu.unq.groupl.app.model.EventTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventTransactionDTO {
	
	private String date;
	private String email;
	
	public EventTransactionDTO(EventTransaction eventTransaction, String zone) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		this.date = Instant.parse(eventTransaction.getInstant()).atZone(ZoneId.of(zone)).format(formatter).replace(' ', 'T');
		this.email = eventTransaction.getUser().getEmail();
	}

}
