package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.service.EventService;

public class CrowdFundingCommonAccountTest {
	private EventService eventHandler;
	private CrowdFundingCommonAccount testCrowdFundingCommonAccount;
	
	@Before
	public void before() {
		eventHandler = new EventService();
		testCrowdFundingCommonAccount = TestBuilder.testCrowdFundingCommonAccount().validCrowdFundingCommonAccount().build();
		testCrowdFundingCommonAccount.setCommonAccount(new Account());
	}

	@Test
	public void testCreateCrowdFundingCommonAccountMustSendInvitations() throws InvalidParameterException {
		CrowdFundingCommonAccount crowdFundingCommonAccountMock = mock(CrowdFundingCommonAccount.class);
		eventHandler.createCrowdFundingCommonAccount(crowdFundingCommonAccountMock);
		verify(crowdFundingCommonAccountMock, times(1)).sendInvitations();
	}
	
	@Test
	public void testGetBalance() {
		assertTrue(testCrowdFundingCommonAccount.getBalance() == 0);
	}
	
	@Test
	public void testGetFunds() throws InvalidAmount {
		testCrowdFundingCommonAccount.addFunds(200);
		testCrowdFundingCommonAccount.getFunds(100);
		assertTrue(testCrowdFundingCommonAccount.getBalance() == 100);
	}

	@Test
	public void testAddFundsBalance() {
		testCrowdFundingCommonAccount.addFunds(100);
		assertTrue(testCrowdFundingCommonAccount.getBalance() == 100);
	}
	
	@Test
	public void testAddFundsFromService() throws InvalidAmount {
		testCrowdFundingCommonAccount.getOwner().getAccount().addMoney(400);
		eventHandler.addFunds(testCrowdFundingCommonAccount, testCrowdFundingCommonAccount.getOwner(), 100);
		assertTrue(testCrowdFundingCommonAccount.getBalance() == 100);
		assertTrue(testCrowdFundingCommonAccount.getOwner().getAccount().getBalance() == 300);
	}
}
