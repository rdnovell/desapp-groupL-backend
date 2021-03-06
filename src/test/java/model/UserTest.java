package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import ar.edu.unq.groupl.app.service.UserService;

public class UserTest {

	private User testUser; 
	private UserService userHandler;
	@Mock
	private UserRepository userRepositoryMock;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-zA-Z]).{4,10})";
	 
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		userHandler = new UserService();
		ReflectionTestUtils.setField(userHandler, "userRepository", userRepositoryMock);
		testUser = TestBuilder.testUser().validUser().build();

	}
	
	// Validaciones
	// Nombre: Texto - Max 30 - Requerido.
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithoutNameMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setName(null);
		userHandler.createUser(testUser);
	}
	
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithNameWithMore30CharsMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setName("Juan carlos la mona gimenez de cordoba capital");
		userHandler.createUser(testUser);
	}
	
	@Test
	public void testUserWithValidName() {
		assertTrue(testUser.getName().length() > 0 && testUser.getName().length() < 30);
	}

	// Apellido: Texto - Max 30 - Requerido.
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithoutLastNameMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setLastName("");
		userHandler.createUser(testUser);
	}
	
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithLastNameWithMore30CharsMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setLastName("Juan carlos la mona gimenez de cordoba capital");
		userHandler.createUser(testUser);
	}
	
	@Test
	public void testUserWithValidLastName() {
		assertTrue(testUser.getLastName().length() > 0 && testUser.getLastName().length() < 30);
	}
	
	// Email: Formato_email - Requerido
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithoutEmailMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setEmail("");
		userHandler.createUser(testUser);
	}
	
	//@Test(expected = InvalidParameterException.class)
	public void testUserWithInvalidEmailMustThrowInvalidParameterException() throws InvalidParameterException {
		testUser.setEmail("alfa.com");
		userHandler.createUser(testUser);
	}
	
	@Test
	public void testUserWithValidEmail() {
		assertTrue(Pattern.compile(EMAIL_PATTERN).matcher(testUser.getEmail()).matches());
	}
	
	@Test
	public void testUserDutiful() {
		assertTrue(testUser.isDutiful());
	}
	
	@Test
	public void testUserNotDutiful() {
		testUser.addDutiful(false);
		assertFalse(testUser.isDutiful());
	}
	
	@Test
	public void testEventsFilter() {
		Party event1 = TestBuilder.testParty().validFiesta().build();
		Party event2 = TestBuilder.testParty().validFiesta().build();
		
		List<Event> eventsAssisted = new ArrayList<Event>();
		eventsAssisted.add(event1);
		eventsAssisted.add(event2);
		testUser.setEventsAssisted(eventsAssisted);
		
		assertTrue(testUser.getEventsInCourse().size() == 2);
		assertTrue(testUser.getEventsCoursed().size() == 0);
		
		event1.setDate(LocalDate.now().minusDays(12));
		assertTrue(testUser.getEventsInCourse().size() == 1);
		assertTrue(testUser.getEventsCoursed().size() == 1);
	}
}
