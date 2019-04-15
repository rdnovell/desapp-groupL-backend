package model;

import static org.junit.Assert.*;

import ar.edu.unq.grupol.app.model.Fiesta;
import org.junit.Before;
import org.junit.Test;

public class FiestaTest {

    public Fiesta testFiesta;

    @Before
    public void before() {
        testFiesta = TestBuilder.testEvento().validFiesta().build();
    }

    @Test
    public void testObtenerTitulo() {
        assertEquals(testFiesta.getTitulo(), "Asado con amigos");
    }

    @Test
    public void testCantidadDeInvitados() {
        assertEquals(testFiesta.getInvitados().size(), 1);
    }

    @Test
    public void testCantidadDeInvitadosConfirmados() {
        assertEquals(testFiesta.getInvitadosConfirmados().size(), 0);
    }

    @Test
    public void testObtenerCostoTotal() {
        assertEquals(testFiesta.getCosto(),0);
    }

    @Test
    public void testAlAgregarUnInvitadoSeModificaElCostoTotal() {
        testFiesta.agregarInvitadoConfirmado(TestBuilder.testUser().validUser().build());
        assertEquals(testFiesta.getCosto(),100);
    }
}
