package ar.edu.unq.grupol.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import ar.edu.unq.grupol.app.model.Transaction;
import ar.edu.unq.grupol.app.model.TransactionType;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import lombok.Getter;

public class MoneyExternalService extends Observable {
	
	@Getter
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public void addMoney(User user, Integer amount) {
		user.getAccount().addMoney(amount);
		transactions.add(new Transaction(TransactionType.ADDFUND, LocalDateTime.now(), amount, user));
		if (user.hasMoneyLoans()) {
			setChanged();
			notifyObservers(user);
		}
	}

	public void getMoney(User user, Integer amount) throws InvalidAmount {
		user.getAccount().getMoney(amount);
		transactions.add(new Transaction(TransactionType.GETFUND, LocalDateTime.now(), amount, user));
	}
	
	public List<Transaction> getAccountBalance(User user) {
		return transactions.stream().filter(transaction -> transaction.getUser().getId() == user.getId()).collect(Collectors.toList());
	}
}
