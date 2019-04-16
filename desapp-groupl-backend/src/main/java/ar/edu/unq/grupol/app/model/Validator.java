package ar.edu.unq.grupol.app.model;

import java.util.regex.Pattern;
import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;

public class Validator {

	public static void validateUser(User user) throws InvalidParameterException {
		validateUserNombreOApellido(user.getNombre(), "nombre");
		validateUserNombreOApellido(user.getApellido(), "apellido");
		validateUserEmail(user);
		validateUserPasword(user);
	}

	private static void validateUserPasword(User user) throws InvalidParameterException {
		final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-zA-Z]).{4,10})";
		
		if (!Pattern.compile(PASSWORD_PATTERN).matcher(user.getPassword()).matches())
			throw new InvalidParameterException("Usuario con password con formato invalido");
	}

	private static void validateUserEmail(User user) throws InvalidParameterException {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{0,})$";
		
		if (!Pattern.compile(EMAIL_PATTERN).matcher(user.getEmail()).matches())
			throw new InvalidParameterException("Usuario con mail con formato invalido");
	}

	private static void validateUserNombreOApellido(String valor, String campo) throws InvalidParameterException {
		if (valor.length() <= 0 || valor.length() > 30)
			throw new InvalidParameterException("Usuario con " + campo + " invalido");
	}
}
