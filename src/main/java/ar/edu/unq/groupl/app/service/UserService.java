package ar.edu.unq.groupl.app.service;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Transaction;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.AccountRepository;
import ar.edu.unq.groupl.app.persistence.TransactionRepository;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import ar.edu.unq.groupl.app.service.annotation.Log;
import ar.edu.unq.groupl.app.service.annotation.Valid;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired private MoneyLoanService moneyLoanService;
	@Autowired private UserRepository userRepository;
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;
	@Autowired private MoneyExternalService moneyExternalService;

	@Transactional
	@Valid
	public void createUser(User user) throws InvalidParameterException {
		if (nonExistentUser(user.getEmail())) {
			user.setMoneyLoanService(moneyLoanService);
			userRepository.save(user);
		}
	}

	@Log
	public int getBalance(String email) throws UnexistException {
		return accountRepository.getBalance(email).orElseThrow(() -> getUnexistException(email));
	}

	public List<Event> getGuestEvents(String email) throws UnexistException {
		if (nonExistentUser(email)) {
			throw getUnexistException(email);
		}
		return userRepository.getGuestEvents(email);
	}
	
	private UnexistException getUnexistException(String email) {
		return new UnexistException("There are no user registered with email '" + email + "'.");
	}
	
	private boolean nonExistentUser(String email) {
		return! userRepository.existsById(email);
	}

	public List<Event> getOwnerEvents(String email) throws UnexistException {
		if (nonExistentUser(email)) {
			throw getUnexistException(email);
		}
		return userRepository.getOwnerEvents(email);
	}

	public List<Event> getAssitedEvents(String email) throws UnexistException {
		if (nonExistentUser(email)) {
			throw getUnexistException(email);
		}
		return userRepository.getAssistedEvents(email);
	}

	public List<Transaction> getAccountTransaction(String email) throws UnexistException {
		if (nonExistentUser(email)) {
			throw getUnexistException(email);
		}

		return transactionRepository.getAccountTransaction(email);
	}

    public User getUser(String email) throws UnexistException {
		if (nonExistentUser(email)) {
			throw getUnexistException(email);
		}
		return userRepository.findById(email).get();
    }
}