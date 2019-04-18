package ar.edu.unq.grupol.app.service;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.Validator;

public class UserService {

	public User createUser(User user) throws InvalidParameterException {
        Validator.validateUser(user);
        return user;
	}

}
