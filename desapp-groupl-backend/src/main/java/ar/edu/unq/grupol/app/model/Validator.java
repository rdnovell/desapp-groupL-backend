package ar.edu.unq.grupol.app.model;

import java.util.regex.Pattern;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;

public class Validator {

	public static void validateUser(User user) throws InvalidParameterException {
		validateUserNombre(user);
		validateUserApellido(user);
		validateUserEmail(user);
		validateUserPasword(user);
	}

	private static void validateUserPasword(User user) throws InvalidParameterException {
		final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-zA-Z]).{4,10})";
		
		if (user.getPassword().length() < 4 ) throw new InvalidParameterException("Usuario con password invalido");
		if (user.getPassword().length() > 10 ) throw new InvalidParameterException("Usuario con password invalido");
		if (!Pattern.compile(PASSWORD_PATTERN).matcher(user.getPassword()).matches()) throw new InvalidParameterException("Usuario con password con formato invalido");
	}

	private static void validateUserEmail(User user) throws InvalidParameterException {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		if (user.getEmail().length() <= 0 ) throw new InvalidParameterException("Usuario con mail invalido");
		if (!Pattern.compile(EMAIL_PATTERN).matcher(user.getEmail()).matches()) throw new InvalidParameterException("Usuario con mail con formato invalido");
	}

	private static void validateUserNombre(User user) throws InvalidParameterException {
		if (user.getNombre().length() <= 0 || user.getNombre().length() > 30) throw new InvalidParameterException("Usuario con nombre invalido");
	}

	private static void validateUserApellido(User user) throws InvalidParameterException {
		if (user.getApellido().length() <= 0 || user.getApellido().length() > 30) throw new InvalidParameterException("Usuario con apellido invalido");
	}
	
}
