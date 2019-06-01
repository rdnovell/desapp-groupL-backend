package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.GuestNotFoundException;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.PartyRepository;
import ar.edu.unq.groupl.app.service.EmailSender;
import ar.edu.unq.groupl.app.service.EventService;

public class FiestaTest {

	private Party testFiesta;
	private EventService eventHandler;

	@Before
	public void before() {
		testFiesta = TestBuilder.testParty().validFiesta().build();
		eventHandler = new EventService();
	}

	@Test
	public void testOnCreatePartyMustSendInvitations() throws InvalidParameterException {
		Party partyMock = mock(Party.class);
	    PartyRepository repoMock = mock(PartyRepository.class);
	    ReflectionTestUtils.setField(eventHandler, "partyRepository", repoMock);
		ReflectionTestUtils.setField(partyMock, "expirationDate", LocalDate.now());
//		eventHandler.createParty(partyMock);
		verify(partyMock, times(1)).sendInvitations();
	}

	@Test
	public void testGetOwner() {
		assertEquals(testFiesta.getOwner().getName(), "Pedro");
	}

	@Test
	public void testGetTitle() {
		assertEquals(testFiesta.getTitle(), "Asado con amigos");
	}

	@Test(expected = InvalidParameterException.class)
	public void testPartyMustHaveValidExpirationDate() throws InvalidParameterException {
		testFiesta.setExpirationDate(null);
//		eventHandler.createParty(testFiesta);
	}

	@Test(expected = EventException.class)
	public void testCannotAddGuestToExpiredParty() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		testFiesta.setExpirationDate(LocalDate.now().minusDays(10));
		testFiesta.addConfirmedGuests(user);
	}
	
	@Test
	public void testGuestsSize() {
		assertEquals(testFiesta.getGuests().size(), 1);
	}

	@Test
	public void testConfirmedGuestsSize() {
		assertEquals(testFiesta.getConfirmedGuests().size(), 0);
	}

	@Test
	public void testGetPartyCost() {
		assertEquals(testFiesta.getPartyCost(), 0);
	}

	@Test
	public void testWhenAddGuestChangePartyCost() throws EventException {
		testFiesta.addConfirmedGuests(TestBuilder.testUser().validUser().build());
		assertEquals(testFiesta.getPartyCost(), 100);
	}
	
	@Test(expected = GuestNotFoundException.class)
	public void testGuestCannotBeConfirmedIfNtotInvited() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		user.setEmail("otheremail@gmail.com");
		testFiesta.addConfirmedGuests(user);
	}
	
	@Test
	public void testCheckIfGuestIsConfirmed() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		testFiesta.addConfirmedGuests(user);
		assertTrue(testFiesta.userIsConfirmated(user));
	}
	
	@Test
	public void testSendInvitationsWhenNotHaveGuestsNotSendMails() {
		Party party = new Party();
		party.setGuests(new ArrayList<User>());
		EmailSender emailSender = mock(EmailSender.class);
		party.setEmailSender(emailSender);
		party.sendInvitations();
		verify(emailSender, times(0)).send(any());
	}
	
	@Test
	public void testSendInvitationsWhenHaveGuestSendMail() {
		Party party = new Party();
		User user = mock(User.class);
		when(user.getName()).thenReturn("Juan");
		when(user.getLastName()).thenReturn("Perez");
		when(user.getEmail()).thenReturn("juanperez@gmail.com");
		party.setGuests(new ArrayList<User>(Arrays.asList(user)));
		EmailSender emailSender = mock(EmailSender.class);
		doNothing().when(emailSender).send(any());
		party.setEmailSender(emailSender);
		party.sendInvitations();
		verify(emailSender, times(1)).send(any());
	}
	
}
