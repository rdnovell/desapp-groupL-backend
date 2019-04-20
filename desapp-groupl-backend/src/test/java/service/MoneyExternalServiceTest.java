package service;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import ar.edu.unq.grupol.app.model.Transaction;
import ar.edu.unq.grupol.app.model.TransactionType;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import ar.edu.unq.grupol.app.service.MoneyExternalService;
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
	public void testUnaCuentaSeCreaSinMovimientos() {
		assertEquals(testMoneyExternalService.getTransactions().size(), 0);
	}

	@Test
	public void testAlAgregarDineroSeRegistraLaTransaccion() {
		testMoneyExternalService.addMoney(testUser, 100);
		Transaction transaction = testMoneyExternalService.getTransactions().get(0);
		assertTrue(transaction.getAmount() == 100);
		assertEquals(transaction.getType(), TransactionType.ADDFUND);
		assertEquals(transaction.getUser(), testUser);
		assertEquals(transaction.getDate().getDayOfMonth(), LocalDate.now().getDayOfMonth());
		assertEquals(testMoneyExternalService.getTransactions().size(), 1);
	}

	@Test
	public void testAgregadoDeDineroAUnaCuenta() {
		testMoneyExternalService.addMoney(testUser, 100);
		assertTrue(testUser.getAccount().getBalance() == 100);
	}

	@Test
	public void testAlRetirarDineroElBalanceSeReduce() throws InvalidAmount {
		testMoneyExternalService.addMoney(testUser,100);
		testMoneyExternalService.getMoney(testUser,30);
		assertTrue(testUser.getAccount().getBalance() == 70);
		assertTrue(testMoneyExternalService.getAccountBalance(testUser).size() == 2);
	}

	@Test(expected = InvalidAmount.class)
	public void testNoSeOuedeRetirarMasDelBalance() throws InvalidAmount {
		testMoneyExternalService.addMoney(testUser,100);
		testMoneyExternalService.getMoney(testUser,1000);
	}

}
