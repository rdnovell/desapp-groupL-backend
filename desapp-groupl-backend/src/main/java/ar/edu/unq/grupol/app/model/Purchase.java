package ar.edu.unq.grupol.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Purchase {

	User user;
	Integer amount;
	
	public void addAmount(Integer amount) {
		this.amount += amount;
	}
	
}
