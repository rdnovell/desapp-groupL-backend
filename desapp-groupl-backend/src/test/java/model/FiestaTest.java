package model;

import static org.junit.Assert.*;

import ar.edu.unq.grupol.app.model.Party;
import org.junit.Before;
import org.junit.Test;

public class FiestaTest {

    public Party testFiesta;

    @Before
    public void before() {
        testFiesta = TestBuilder.testEvento().validFiesta().build();
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
