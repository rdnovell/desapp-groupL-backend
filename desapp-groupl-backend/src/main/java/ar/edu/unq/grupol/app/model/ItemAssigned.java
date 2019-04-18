package ar.edu.unq.grupol.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ItemAssigned {

	@Setter private User user;
	private Item item;
	
}
