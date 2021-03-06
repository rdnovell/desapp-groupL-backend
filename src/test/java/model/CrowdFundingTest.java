package model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.CrowdRepository;
import ar.edu.unq.groupl.app.service.EventService;

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
		user2.setName("Juan");

		testCrowdFunding.addConfirmedGuests(user2);
	}

	@Test
	public void testCreateCrowdFundingMustSendInvitations() throws InvalidParameterException {
		CrowdFunding crowdFundingMock = mock(CrowdFunding.class);
		CrowdRepository repoMock = mock(CrowdRepository.class);
		ReflectionTestUtils.setField(eventHandler, "crowdRepository", repoMock);
		eventHandler.createCrowdFunding(crowdFundingMock);
		verify(crowdFundingMock, times(1)).sendInvitations();
	}

	@Test
	public void testGetItemPurchases() {
		assertTrue(testCrowdFunding.getItemPurchases().size() == 0);
	}

	@Test
	public void testAddItemPurchaseMustIncrementItemPurchasesSize() {
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 30);
		assertTrue(testCrowdFunding.getItemPurchases().size() == 1);
	}

	@Test
	public void testGetCostOfUser() {
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 30);
		testCrowdFunding.addItemPurchase(testCrowdFunding.getOwner(), 10);
		assertTrue(testCrowdFunding.getCost(testCrowdFunding.getOwner()) == 10);
	}

	@Test
	public void testHowMuchMoneyMustPayEveryUser() {
		assertTrue(testCrowdFunding.getCostPerUser() == 50);
		assertTrue(testCrowdFunding.getCost(user2) == 50);
	}
}
