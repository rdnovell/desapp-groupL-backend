package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.Transaction;
import ar.edu.unq.groupl.app.model.TransactionType;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.service.MoneyExternalService;
import ar.edu.unq.groupl.app.service.MoneyLoanService;
import model.TestBuilder;

public class MoneyExternalServiceTest {
	private MoneyExternalService testMoneyExternalService;
	private User testUser;

	@Before
	public void before() {
		testMoneyExternalService = new MoneyExternalService();
		testUser = TestBuilder.testUser().validUser().build();
	}

	@Test
	public void testAccountIsCreatedWithoutTransactions() {
		assertEquals(testMoneyExternalService.getTransactions().size(), 0);
	}

	@Test
	public void testWhenAddMoneyTransactionIsRegistered() {
		testMoneyExternalService.addMoney(testUser, 100);
		Transaction transaction = testMoneyExternalService.getTransactions().get(0);
		assertTrue(transaction.getAmount() == 100);
		assertEquals(transaction.getType(), TransactionType.ADDFUND);
		assertEquals(transaction.getUser(), testUser);
		assertEquals(transaction.getDate().getDayOfMonth(), LocalDate.now().getDayOfMonth());
		assertEquals(testMoneyExternalService.getTransactions().size(), 1);
	}

	@Test
	public void testAddMoneyToAccount() {
		testMoneyExternalService.addMoney(testUser, 100);
		assertTrue(testUser.getAccount().getBalance() == 100);
	}

	@Test
	public void testWhenGetMoneyAccountBalanceIsReduced() throws InvalidAmount {
		testMoneyExternalService.addMoney(testUser,100);
		testMoneyExternalService.getMoney(testUser,30);
		assertTrue(testUser.getAccount().getBalance() == 70);
		assertTrue(testMoneyExternalService.getAccountBalance(testUser).size() == 2);
	}

	@Test(expected = InvalidAmount.class)
	public void testWhenGetMoneyMoreThanBalanceMustThrowInvalidAmount() throws InvalidAmount {
		testMoneyExternalService.addMoney(testUser,100);
		testMoneyExternalService.getMoney(testUser,1000);
	}
	
	@Test
	public void testWhenAddMoneyAndUserIsRiskNotifyObservers() {
		MoneyLoanService moneyLoanService = mock(MoneyLoanService.class);
		testMoneyExternalService.addObserver(moneyLoanService);
		User user = mock(User.class);
		when(user.getAccount()).thenReturn(mock(Account.class));
		when(user.hasMoneyLoans()).thenReturn(true);
		testMoneyExternalService.addMoney(user, 100);
		verify(moneyLoanService, times(1)).update(testMoneyExternalService, user);
	}

}
