package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.ItemAssigned;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.BasketRepository;
import ar.edu.unq.groupl.app.service.EventService;

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
		
		item = new Item("Asado", 100);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		testBasket.addItems(items);
	}

	@Test
	public void testOnCreateMustSendInvitations() throws InvalidParameterException {
		Basket basketMock = mock(Basket.class);
	    BasketRepository repoMock = mock(BasketRepository.class);
	    ReflectionTestUtils.setField(eventHandler, "basketRepository", repoMock);
		eventHandler.createBasket(basketMock);
		verify(basketMock, times(1)).sendInvitations();
	}
	
	@Test
	public void testOnAddItemMustBeAvailableToAssignIt() {
		assertEquals(testBasket.getItems().size(), 1);
		assertEquals(testBasket.getItemsAssigned().size(), 1);
	}
	
	@Test
	public void testUserAssignAnItemMustSetUserOnItemAssign() {
		testBasket.assignItem(user, item);
		ItemAssigned itemAssigned = testBasket.getItemsAssigned().get(0);
		assertEquals(itemAssigned.getUser(), user);
	}
	
	@Test
	public void testAssignItemToUserOfAnItemThatDontExistMustDoNothing() {
		ItemAssigned itemAssigned = mock(ItemAssigned.class);
		Item item = mock(Item.class);
		Item anotherItem = mock(Item.class);
		when(itemAssigned.getItem()).thenReturn(item);
		ReflectionTestUtils.setField(testBasket, "itemsAssigned", new ArrayList<ItemAssigned>(Arrays.asList(itemAssigned)));
		testBasket.assignItem(mock(User.class), anotherItem);
		verify(itemAssigned, times(1)).setUser(any(User.class));
	}
	
	@Test
	public void testAssignItemToUserOfAnItemThatWasAssignedMustDoNothing() {
		ItemAssigned itemAssigned = mock(ItemAssigned.class);
		Item item = mock(Item.class);
		User user = mock(User.class);
		when(itemAssigned.getItem()).thenReturn(item);
		when(itemAssigned.getUser()).thenReturn(user);
		ReflectionTestUtils.setField(testBasket, "itemsAssigned", new ArrayList<ItemAssigned>(Arrays.asList(itemAssigned)));
		testBasket.assignItem(mock(User.class), item);
		verify(itemAssigned, times(0)).setUser(user);
	}
	
	@Test
	public void testSetNotDutifulMustChangeTheResultOfAnUserWhenCallsMethodIsDutiful() {
		testBasket.setNotDutiful(user);
		assertFalse(user.isDutiful());
	}
}
