package modelTest;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.UserHandler;

public class UserTest {

	public User testUser; 
	private UserHandler userHandler;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Before
	public void before() {
		this.userHandler = new UserHandler();
		this.testUser = TestBuilder.testUser().validUser().build();

	}
	
	
	// Validaciones
	// Nombre: Texto - Max 30 - Requerido.
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinNombre() throws InvalidParameterException {
		this.testUser.setNombre("");
		this.userHandler.createUser(this.testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConNombreMayorA30() throws InvalidParameterException {
		this.testUser.setNombre("Juan carlos la mona gimenez de cordoba capital");
		this.userHandler.createUser(this.testUser);
	}
	@Test
	public void testUsuarioConNombreValido() {
		assertTrue(this.testUser.getNombre().length() > 0 && this.testUser.getNombre().length() < 30);
	}

	// Apellido: Texto - Max 30 - Requerido.
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinApellido() throws InvalidParameterException {
		this.testUser.setApellido("");
		this.userHandler.createUser(this.testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConApellidoMayorA30() throws InvalidParameterException {
		this.testUser.setApellido("Juan carlos la mona gimenez de cordoba capital");
		this.userHandler.createUser(this.testUser);
	}
	@Test
	public void testUsuarioConApellidoValido() {
		assertTrue(this.testUser.getApellido().length() > 0 && this.testUser.getApellido().length() < 30);
	}
	
	// Email: Formato_email - Requerido
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioSinEmail() throws InvalidParameterException {
		this.testUser.setEmail("");
		this.userHandler.createUser(this.testUser);
	}
	@Test(expected = InvalidParameterException.class)
	public void testUsuarioConEmailInvalido() throws InvalidParameterException {
		this.testUser.setEmail("alfa.com");
		this.userHandler.createUser(this.testUser);
	}
	@Test
	public void testUsuarioConEmailValido() {
		assertTrue(Pattern.compile(EMAIL_PATTERN).matcher(this.testUser.getEmail()).matches());
	}
	
	// Contraseña: Min 4 - Max 10 - Alfanumérico - Requerido.
	//Fecha de Nacimiento : DD/MM/AAAA - Requerido
	
	
}
