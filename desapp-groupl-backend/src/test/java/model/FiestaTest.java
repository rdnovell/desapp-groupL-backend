package model;

import static org.junit.Assert.*;

import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.EventException;
import ar.edu.unq.grupol.app.model.exception.GuestNotFoundException;
import ar.edu.unq.grupol.app.model.exception.InvalidParameterException;
import ar.edu.unq.grupol.app.service.EventService;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class FiestaTest {

	private Party testFiesta;
	private EventService eventHandler;

	@Before
	public void before() {
		testFiesta = TestBuilder.testParty().validFiesta().build();
		eventHandler = new EventService();
	}

	@Test
	public void testEnvioDeNotificacion() throws InvalidParameterException {
		Party partyMock = mock(Party.class);
		ReflectionTestUtils.setField(partyMock, "expirationDate", LocalDate.now());
		eventHandler.createParty(partyMock);
		verify(partyMock, times(1)).sendInvitations();
	}

	@Test
	public void testObtenerOrganizdor() {
		assertEquals(testFiesta.getOwner().getName(), "Pedro");
	}

	@Test
	public void testObtenerTitulo() {
		assertEquals(testFiesta.getTitle(), "Asado con amigos");
	}

	@Test(expected = InvalidParameterException.class)
	public void testLaFiestaTieneUnaFechaDeExpiracionValida() throws InvalidParameterException {
		testFiesta.setExpirationDate(null);
		eventHandler.createParty(testFiesta);
	}

	@Test(expected = EventException.class)
	public void testNoSePuedeAgregarInvitadosSiExpiro() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		testFiesta.setExpirationDate(LocalDate.now().minusDays(10));
		testFiesta.addConfirmedGuests(user);
	}
	
	@Test
	public void testCantidadDeInvitados() {
		assertEquals(testFiesta.getGuests().size(), 1);
	}

	@Test
	public void testCantidadDeInvitadosConfirmados() {
		assertEquals(testFiesta.getConfirmedGuests().size(), 0);
	}

	@Test
	public void testObtenerCostoTotal() {
		assertEquals(testFiesta.getPartyCost(), 0);
	}

	@Test
	public void testAlAgregarUnInvitadoSeModificaElCostoTotal() throws EventException {
		testFiesta.addConfirmedGuests(TestBuilder.testUser().validUser().build());
		assertEquals(testFiesta.getPartyCost(), 100);
	}
	
	@Test(expected = GuestNotFoundException.class)
	public void testNoSePuedeConfirmaAUnNoInvitado() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		user.setId(2);
		testFiesta.addConfirmedGuests(user);
	}
	
	@Test
	public void testVerificarSiUnUsuarioEstaConfirmado() throws EventException {
		User user = TestBuilder.testUser().validUser().build();
		testFiesta.addConfirmedGuests(user);
		assertTrue(testFiesta.userIsConfimated(user));
	}
}
