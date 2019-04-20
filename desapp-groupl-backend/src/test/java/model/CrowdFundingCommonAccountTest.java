package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.Basket;
import ar.edu.unq.grupol.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import ar.edu.unq.grupol.app.service.EventService;

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
	public void testEnvioDeNotificacion() {
		CrowdFundingCommonAccount crowdFundingCommonAccountMock = mock(CrowdFundingCommonAccount.class);
		eventHandler.createCrowdFundingCommonAccount(crowdFundingCommonAccountMock);
		// Esto envia un mail de verdad testFiesta.sendInvitations();
		verify(crowdFundingCommonAccountMock, times(1)).sendInvitations();
	}
	
	@Test
	public void testGetBalance() {
		assertTrue(testCrowdFundingCommonAccount.getBalance() == 0);
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
