package model;

import static org.junit.Assert.*;

import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.EmailSender;
import ar.edu.unq.grupol.app.model.Event;
import ar.edu.unq.grupol.app.model.EventService;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class FiestaTest {

	private Party testFiesta;
    private EventService eventHandler;
    
    @Before
    public void before() {
        testFiesta = TestBuilder.testParty().validFiesta().build();
        eventHandler = new EventService();
    }
    
    @Test
    public void testEnvioDeNotificacion() {
    	Event eventMock = mock(Event.class);
    	eventHandler.createEvent(eventMock);
    	//Esto envia un mail de verdad testFiesta.sendInvitations();
    	verify(eventMock, times(1)).sendInvitations();
    }

    @Test
    public void testObtenerOrganizdor() {
        assertEquals(testFiesta.getOwner().getName(), "Pedro");
    }

    @Test
    public void testObtenerTitulo() {
        assertEquals(testFiesta.getTitle(), "Asado con amigos");
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
    public void testAlAgregarUnInvitadoSeModificaElCostoTotal() {
        testFiesta.addConfirmedGuests(TestBuilder.testUser().validUser().build());
        assertEquals(testFiesta.getPartyCost(), 100);
    }
}
