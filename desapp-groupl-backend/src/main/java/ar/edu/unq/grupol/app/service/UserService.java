package ar.edu.unq.grupol.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.Validator;
import ar.edu.unq.grupol.app.model.exception.InvalidParameterException;
import ar.edu.unq.grupol.app.persistence.UserRepository;

@Component
public class UserService {

	@Autowired private MoneyLoanService moneyLoanService;
	@Autowired private UserRepository userRepository;
	
	public User createUser(User user) throws InvalidParameterException {
		Validator.validateUser(user);
		user.setMoneyLoanService(moneyLoanService);
		userRepository.save(user);
		return user;
	}

}
