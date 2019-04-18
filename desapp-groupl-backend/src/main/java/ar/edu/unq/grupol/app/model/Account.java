package ar.edu.unq.grupol.app.model;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import lombok.Getter;

@Getter
public class Account {

	Integer balance;
	List<Transaction> transactions;
	
	public void addMoney(Integer amount) {
		balance += amount;
		transactions.add(new Transaction(TransactionType.ADDFUND, LocalDateTime.now(), amount));
	}
	
	public void getMoney(Integer amount) throws InvalidAmount {
		if (balance >= amount) {
			balance -= amount;
			transactions.add(new Transaction(TransactionType.GETFUND, LocalDateTime.now(), amount));
		} else {
			throw new InvalidAmount("Transaction cannot be done, insufficient balance.");
		}
	}
	
}
