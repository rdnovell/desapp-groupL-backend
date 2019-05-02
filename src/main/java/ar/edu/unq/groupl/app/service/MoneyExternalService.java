package ar.edu.unq.groupl.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Transaction;
import ar.edu.unq.groupl.app.model.TransactionType;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import lombok.Getter;

@Component
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
		return ListUtil.toList(transactions.stream().filter(transaction -> transaction.getUser().equals(user)));
	}
	
}