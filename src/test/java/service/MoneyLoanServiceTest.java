package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.unq.groupl.app.model.CreditSituationType;
import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.service.MoneyExternalService;
import ar.edu.unq.groupl.app.service.MoneyLoanService;
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
	public void testServiceStartWithoutLoans() {
		assertEquals(testMoneyLoanService.getLoans().size(), 0);
	}

	@Test
	public void testCreateLoanUserDutiful() {
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

	@Test(expected = NoSuchElementException.class)
	public void testUserGetLoanException() {
		testMoneyLoanService.getLoan(testUser);
	}
	
	@Test
	public void testUserHasLoanException() {
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
	
	@Test
	public void testUpdateWhenUserIsRiskCallsPayLoan() {
		Loan loan = prepareLoanMock(true);
		MoneyExternalService moneyExternalService = mock(MoneyExternalService.class);
		MoneyLoanService moneyLoanServiceSpy = spy(testMoneyLoanService);
		doNothing().when(moneyLoanServiceSpy).payLoan(loan);
		moneyLoanServiceSpy.update(moneyExternalService, loan.getUser());
		verify(moneyLoanServiceSpy, times(1)).payLoan(loan);
	}
	
	@Test
	public void testUpdateWhenUserIsRiskDontCallSPayLoan() {
		Loan loan = prepareLoanMock(false);
		MoneyExternalService moneyExternalService = mock(MoneyExternalService.class);
		MoneyLoanService moneyLoanServiceSpy = spy(testMoneyLoanService);
		doNothing().when(moneyLoanServiceSpy).payLoan(loan);
		moneyLoanServiceSpy.update(moneyExternalService, loan.getUser());
		verify(moneyLoanServiceSpy, times(0)).payLoan(loan);
	}
	
	private Loan prepareLoanMock(Boolean loanIsRisk) {
		List<Loan> loans = new ArrayList<Loan>();
		Loan loan = mock(Loan.class);
		loans.add(loan);
		ReflectionTestUtils.setField(testMoneyLoanService, "loans", loans);
		User user = mock(User.class);
		when(loan.isRisk()).thenReturn(loanIsRisk);
		when(user.getId()).thenReturn(1);
		when(loan.getUser()).thenReturn(user);
		return loan;
	}
	
}
