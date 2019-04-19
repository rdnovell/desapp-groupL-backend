package model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.model.CrowdFunding;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.EventException;
import ar.edu.unq.grupol.app.service.EventService;

public class CrowdFundingTest {
	private EventService eventHandler;
	private CrowdFunding testCrowdFunding;
	private User user2;
	@Before
	public void before() throws EventException {
		eventHandler = new EventService();
		testCrowdFunding = TestBuilder.testCrowdFunding().validCrowdFunding().build();
		testCrowdFunding.addConfirmedGuests(testCrowdFunding.getOwner());
		
		user2 = TestBuilder.testUser().validUser().build();
		user2.setId(2);
		user2.setName("Juan");
		
		testCrowdFunding.addConfirmedGuests(user2);
	}

	@Test
	public void testEnvioDeNotificacion() {
		CrowdFunding crowdFundingMock = mock(CrowdFunding.class);
		eventHandler.createCrowdFunding(crowdFundingMock);
		// Esto envia un mail de verdad testFiesta.sendInvitations();
		verify(crowdFundingMock, times(1)).sendInvitations();
	}
	
	@Test
	public void testGetItemPurchases() {
		assertTrue(testCrowdFunding.getItemPurchases().size() == 0);
	}
	
	@Test
	public void testUnUserSeleccionaUnItemParaComprar() {
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 30);
		assertTrue(testCrowdFunding.getItemPurchases().size() == 1);
	}
	
	@Test
	public void testCuantoDebePagarUnUser() {
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 30);
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 10);
		assertTrue(testCrowdFunding.getCost(testCrowdFunding.getOwner()) == 10);
	}
	
	@Test
	public void testCuantoDebePagarCadaUsuarioSiNoCompraNada() {
		assertTrue(testCrowdFunding.getCostPerUser() == 50);
		assertTrue(testCrowdFunding.getCost(user2) == 50);
	}
}
