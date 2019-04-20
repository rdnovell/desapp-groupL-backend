package service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ar.edu.unq.grupol.app.model.CreditSituationType;
import ar.edu.unq.grupol.app.model.Loan;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.service.MoneyLoanService;
import model.TestBuilder;

public class MoneyLoanServiceTest {
	private MoneyLoanService testMoneyLoanService;
	private User testUser;
	
	@Before
	public void before() {
		testMoneyLoanService = new MoneyLoanService();
		testUser = TestBuilder.testUser().validUser().build();
	}

	@Test
	public void testElServicioSeCreaSinMovimientos() {
		assertEquals(testMoneyLoanService.getLoans().size(), 0);
	}
	
	@Test
	public void testCrearLoanUserDutiful() {
		testMoneyLoanService.createLoan(testUser);
		assertEquals(testMoneyLoanService.getLoans().size(), 1);
	}
	
	@Test
	public void testGetLoan() {
		testMoneyLoanService.createLoan(testUser);
		Loan loan = testMoneyLoanService.getLoan(testUser);
		assertEquals(loan.getUser().getId(), testUser.getId());
		assertTrue(loan.getLoanAmount() == 1000);
		assertTrue(loan.getLoanTerm() == 6);
	}
	
	@Test
	public void testUserHasNotLoan() {
		assertFalse(testMoneyLoanService.hasMoneyLoans(testUser));
	}
	
	@Test
	public void testUserHasLoan() {
		testMoneyLoanService.createLoan(testUser);
		assertTrue(testMoneyLoanService.hasMoneyLoans(testUser));
	}
	
	@Test
	public void testPayLoan() {
		testMoneyLoanService.createLoan(testUser);
		Loan loan = testMoneyLoanService.getLoan(testUser);
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoan(loan);
		assertTrue(testUser.getAccount().getBalance() == 100);
		assertTrue(loan.getLoanTermsPayed() == 1);
	}
	
	@Test
	public void testPayLoanAndFinish() {
		testMoneyLoanService.createLoan(testUser);
		Loan loan = testMoneyLoanService.getLoan(testUser);
		ReflectionTestUtils.setField(loan, "loanTermsPayed", 5);
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoan(loan);
		assertTrue(testUser.getAccount().getBalance() == 100);
		assertEquals(testMoneyLoanService.getLoans().size(), 0);
	}
	
	@Test
	public void testPayLoanNotMoney() {
		testMoneyLoanService.createLoan(testUser);
		Loan loan = testMoneyLoanService.getLoan(testUser);
		testUser.getAccount().addMoney(100);
		testMoneyLoanService.payLoan(loan);
		assertEquals(loan.getCreditSituation(), CreditSituationType.RISK);
	}
	
	@Test
	public void testPayLoanIsRisk() {
		testMoneyLoanService.createLoan(testUser);
		Loan loan = testMoneyLoanService.getLoan(testUser);
		testUser.getAccount().addMoney(100);
		testMoneyLoanService.payLoan(loan);
		assertTrue(loan.isRisk());
	}
	
	@Test
	public void testPayLoans() {
		testMoneyLoanService.createLoan(testUser);
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoans();
		assertTrue(testUser.getAccount().getBalance() == 100);
	}
}
