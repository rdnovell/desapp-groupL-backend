package ar.edu.unq.groupl.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Purchase {

	private User user;
	private Integer amount;
	
	public void addAmount(Integer amount) {
		this.amount += amount;
	}
	
}