package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.ItemAssigned;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
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
		
		item = new Item(1, "Asado", 100);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		testBasket.addItems(items);
	}

	@Test
	public void testOnCreateMustSendInvitations() throws InvalidParameterException {
		Basket basketMock = mock(Basket.class);
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
		assertEquals(itemAssigned.getUser().getId(), user.getId());
	}
	
	@Test
	public void testAssignItemToUserOfAnItemThatDontExistMustDoNothing() {
		ItemAssigned itemAssigned = mock(ItemAssigned.class);
		Item item = mock(Item.class);
		Item anotherItem = mock(Item.class);
		when(anotherItem.getId()).thenReturn(2);
		when(itemAssigned.getItem()).thenReturn(item);
		when(item.getId()).thenReturn(1);
		ReflectionTestUtils.setField(testBasket, "itemsAssigned", new ArrayList<ItemAssigned>(Arrays.asList(itemAssigned)));
		testBasket.assignItem(mock(User.class), anotherItem);
		verify(itemAssigned, times(0)).setUser(any(User.class));
	}
	
	@Test
	public void testAssignItemToUserOfAnItemThatWasAssignedMustDoNothing() {
		ItemAssigned itemAssigned = mock(ItemAssigned.class);
		Item item = mock(Item.class);
		User user = mock(User.class);
		when(itemAssigned.getItem()).thenReturn(item);
		when(itemAssigned.getUser()).thenReturn(user);
		when(item.getId()).thenReturn(1);
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
