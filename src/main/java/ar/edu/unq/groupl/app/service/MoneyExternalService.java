package ar.edu.unq.groupl.app.service;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.Transaction;
import ar.edu.unq.groupl.app.model.TransactionType;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.persistence.AccountRepository;
import ar.edu.unq.groupl.app.persistence.TransactionRepository;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@Component
public class MoneyExternalService extends Observable {

	@Autowired private UserRepository userRepository;
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;


	@Getter
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public void addMoney(User user, Integer amount) {
		String dateCode = String.valueOf(LocalDateTime.now().getYear()) + String.valueOf(LocalDateTime.now().getMonthValue());
		user.getAccount().addMoney(amount);
		transactions.add(new Transaction(TransactionType.ADDFUND, LocalDateTime.now(), dateCode, amount, user));
		if (user.hasMoneyLoans()) {
			setChanged();
			notifyObservers(user);
		}
	}

	public void getMoney(User user, Integer amount) throws InvalidAmount {
		String dateCode = String.valueOf(LocalDateTime.now().getYear()) + String.valueOf(LocalDateTime.now().getMonthValue());
		user.getAccount().getMoney(amount);
		transactions.add(new Transaction(TransactionType.GETFUND, LocalDateTime.now(), dateCode, amount, user));
	}
	
	public List<Transaction> getAccountBalance(User user) {
		return ListUtil.toList(transactions.stream().filter(transaction -> transaction.getEmail().equals(user.getEmail())));
	}

	@Transactional
	public void addTransaction(User u, Transaction t){
		User user = userRepository.findById(u.getEmail()).get();
		Account a = accountRepository.findById(u.getEmail()).get();

		//a.setBalance();
		user.getAccount().setBalance(t.getBalance());

		//accountRepository.save(a);
		transactionRepository.save(t);
		userRepository.save(user);

		user = userRepository.findById(u.getEmail()).get();
		a = accountRepository.findById(u.getEmail()).get();

	}

}