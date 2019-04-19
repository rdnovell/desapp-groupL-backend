package model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.Transaction;
import ar.edu.unq.grupol.app.model.TransactionType;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;

public class AccountTest {

	private Account testAccount;

	@Before
	public void before() {
		testAccount = new Account();
	}

	@Test
	public void testUnaCuentaSeCreaSinBalance() {
		assertTrue(testAccount.getBalance() == 0);
	}

	@Test
	public void testUnaCuentaSeCreaSinMovimientos() {
		assertEquals(testAccount.getTransactions().size(), 0);
	}

	@Test
	public void testAlAgregarDineroSeRegistraLaTransaccion() {
		testAccount.addMoney(100);
		Transaction transaction = testAccount.getTransactions().get(0);
		assertTrue(transaction.getAmount() == 100);
		assertEquals(transaction.getType(), TransactionType.ADDFUND);
		assertEquals(transaction.getDate().getDayOfMonth(), LocalDate.now().getDayOfMonth());
		assertEquals(testAccount.getTransactions().size(), 1);
	}
	
	@Test
	public void testAgregadoDeDineroAUnaCuenta() {
		testAccount.addMoney(100);
		assertTrue(testAccount.getBalance() == 100);
	}
	
	@Test
	public void testAlRetirarDineroElBalanceSeReduce() throws InvalidAmount {
		testAccount.addMoney(100);
		testAccount.getMoney(30);
		assertTrue(testAccount.getBalance() == 70);
	}
	
	@Test(expected = InvalidAmount.class)
	public void testNoSeOuedeRetirarMasDelBalance() throws InvalidAmount {
		testAccount.addMoney(100);
		testAccount.getMoney(1000);
	}
	
}
