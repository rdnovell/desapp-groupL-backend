package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.model.Basket;
import ar.edu.unq.grupol.app.model.Item;
import ar.edu.unq.grupol.app.model.ItemAssigned;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.InvalidParameterException;
import ar.edu.unq.grupol.app.service.EventService;

public class BasketTest {
	private Basket testBasket;
	private EventService eventHandler;
	private Item item;
	private User user;

	@Before
	public void before() {
		testBasket = TestBuilder.testBasket().validBasket().build();
		user = TestBuilder.testUser().validUser().build();
		eventHandler = new EventService();
		
		item = new Item(1, "Asado", 100);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		testBasket.addItems(items);
	}

	@Test
	public void testEnvioDeNotificacion() throws InvalidParameterException {
		Basket basketMock = mock(Basket.class);
		eventHandler.createBasket(basketMock);
		// Esto envia un mail de verdad testFiesta.sendInvitations();
		verify(basketMock, times(1)).sendInvitations();
	}
	
	@Test
	public void testCuandoSeAgregaUnItemEstaDisponibleParaElegirlo() {
		assertEquals(testBasket.getItems().size(), 1);
		assertEquals(testBasket.getItemsAssigned().size(), 1);
	}
	
	@Test
	public void testUnUserEligeUnItem() {
		testBasket.assignItem(user, item);
		
		ItemAssigned itemAssigned = testBasket.getItemsAssigned().get(0);
		assertEquals(itemAssigned.getUser().getId(), user.getId());
	}
	
	@Test
	public void testUnUsuarioNoCumpleDejaDeSerDutiful() {
		testBasket.setNotDutiful(user);
		assertFalse(user.isDutiful());
	}
}
