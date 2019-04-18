package model;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.service.UserService;

public class UserTest {

	private User testUser; 
	private UserService userHandler;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-zA-Z]).{4,10})";
	 
	@Before
	public void before() {
		userHandler = new UserService();
		testUser = TestBuilder.testUser().validUser().build();

	}
	
	// Validaciones
	// Nombre: Texto - Max 30 - Requerido.
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinNombre() throws InvalidParameterException {
		testUser.setName("");
		userHandler.createUser(testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConNombreMayorA30() throws InvalidParameterException {
		testUser.setName("Juan carlos la mona gimenez de cordoba capital");
		userHandler.createUser(testUser);
	}
	@Test
	public void testUsuarioConNombreValido() {
		assertTrue(testUser.getName().length() > 0 && testUser.getName().length() < 30);
	}

	// Apellido: Texto - Max 30 - Requerido.
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinApellido() throws InvalidParameterException {
		testUser.setLastName("");
		userHandler.createUser(testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConApellidoMayorA30() throws InvalidParameterException {
		testUser.setLastName("Juan carlos la mona gimenez de cordoba capital");
		userHandler.createUser(testUser);
	}
	@Test
	public void testUsuarioConApellidoValido() {
		assertTrue(testUser.getLastName().length() > 0 && testUser.getLastName().length() < 30);
	}
	
	// Email: Formato_email - Requerido
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinEmail() throws InvalidParameterException {
		testUser.setEmail("");
		userHandler.createUser(testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConEmailInvalido() throws InvalidParameterException {
		testUser.setEmail("alfa.com");
		userHandler.createUser(testUser);
	}
	@Test
	public void testUsuarioConEmailValido() {
		assertTrue(Pattern.compile(EMAIL_PATTERN).matcher(testUser.getEmail()).matches());
	}
	
	// Contraseña: Min 4 - Max 10 - Alfanumérico - Requerido.
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConPasswordMenorA4() throws InvalidParameterException {
		testUser.setPassword("a12");
		userHandler.createUser(testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConPasswordMayorA10() throws InvalidParameterException {
		testUser.setPassword("alcome12rh345");
		userHandler.createUser(testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConPasswordNoAlpha() throws InvalidParameterException {
		testUser.setPassword("alcone");
		userHandler.createUser(testUser);
	}
	@Test
	public void testUsuarioConPasswordValido() throws InvalidParameterException {
		testUser.setPassword("alco12ne");
		assertTrue(Pattern.compile(PASSWORD_PATTERN).matcher(testUser.getPassword()).matches());
		userHandler.createUser(testUser);
	}
	//Fecha de Nacimiento : DD/MM/AAAA - Requerido
	
	@Test
	public void testUsuarioConFechaDeNacimientoValida() {
		testUser.setPassword("alco12ne");
		assertEquals(testUser.getBirthDate(), "01/01/1985");
	}
}
