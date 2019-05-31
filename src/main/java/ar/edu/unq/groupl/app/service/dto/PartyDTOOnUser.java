package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import ar.edu.unq.groupl.app.model.Party;
import lombok.Getter;

@Getter
public class PartyDTOOnUser extends EventDTOOnUser {
	
	private LocalDate expirationDate;
	
	public PartyDTOOnUser(Party party) {
		super(party);
		this.expirationDate = party.getExpirationDate();
	}
	
}
