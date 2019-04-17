package ar.edu.unq.grupol.app.model;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;

public class UserService {

	public User createUser(User user) throws InvalidParameterException {
        Validator.validateUser(user);
        return user;
	}

}
