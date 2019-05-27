package ar.edu.unq.groupl.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.Validator;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import ar.edu.unq.groupl.app.service.dto.ConverterDTOService;
import ar.edu.unq.groupl.app.service.dto.UserDTO;
import ar.edu.unq.groupl.app.service.exception.LoginException;
import ar.edu.unq.groupl.app.service.exception.UnexistException;

@Service
public class UserService {

	@Autowired private MoneyLoanService moneyLoanService;
	@Autowired private UserRepository userRepository;
	@Autowired private ConverterDTOService converterDTOService;
	
	public User createUser(User user) throws InvalidParameterException {
		Validator.validateUser(user);
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setMoneyLoanService(moneyLoanService);
		userRepository.save(user);
		return user;
	}
	
	public UserDTO login(String email, String password) throws UnexistException, LoginException {
		User user = userRepository.findById(email).orElseThrow(() -> new UnexistException("There are no user registered with email '" + email + "'."));
		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new LoginException("Incorrect password.");
		}
		return converterDTOService.convertUserToDTO(user);
	}

	public int getBalance(String email) throws UnexistException {
		User user = userRepository.findById(email).orElseThrow(() -> new UnexistException("There are no user registered with email '" + email + "'."));
		return user.getAccount().getBalance();
	}

}