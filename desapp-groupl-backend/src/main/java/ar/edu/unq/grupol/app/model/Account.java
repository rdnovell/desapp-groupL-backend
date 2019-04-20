package ar.edu.unq.grupol.app.model;

import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import lombok.Getter;

@Getter
public class Account {

	private Integer balance = 0;
	
	public void addMoney(Integer amount) {
		balance += amount;
	}
	
	public void getMoney(Integer amount) throws InvalidAmount {
		if (balance >= amount) {
			balance -= amount;
		} else {
			throw new InvalidAmount("Transaction cannot be done, insufficient balance.");
		}
	}
	
}
