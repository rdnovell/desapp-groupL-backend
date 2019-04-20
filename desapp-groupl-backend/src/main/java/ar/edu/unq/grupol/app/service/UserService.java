package ar.edu.unq.grupol.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.Validator;

@Component
public class UserService {

	@Autowired
	private MoneyLoanService moneyLoanService;

	public User createUser(User user) throws InvalidParameterException {
		Validator.validateUser(user);
		user.setMoneyLoanService(moneyLoanService);
		return user;
	}

}
