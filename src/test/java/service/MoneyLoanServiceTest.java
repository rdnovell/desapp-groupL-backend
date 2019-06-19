package service;

import ar.edu.unq.groupl.app.model.CreditSituationType;
import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.persistence.LoanRepository;
import ar.edu.unq.groupl.app.service.MoneyLoanService;
import ar.edu.unq.groupl.app.service.UserService;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import model.TestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyLoanServiceTest {
	private MoneyLoanService testMoneyLoanService;
	private User testUser;

	@Before
	public void before() throws UnexistException {
		testMoneyLoanService = new MoneyLoanService();
		LoanRepository repoMock = mock(LoanRepository.class);
		ReflectionTestUtils.setField(testMoneyLoanService, "loanRepository", repoMock);
		testUser = TestBuilder.testUser().validUser().build();
		UserService userMock = mock(UserService.class);
		when(userMock.getUser(testUser.getEmail())).thenReturn(testUser);
		ReflectionTestUtils.setField(testMoneyLoanService, "userService", userMock);
	}

	@Test
	public void testServiceStartWithoutLoans() {
		assertEquals(testMoneyLoanService.getLoans().size(), 0);
	}

	public void testCreateLoanUserDutiful() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		assertEquals(testMoneyLoanService.getLoans().size(), 1);
	}

	public void testGetLoan() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		Loan loan = testMoneyLoanService.getLoan(testUser.getEmail());
		//assertEquals(loan.getUser().getId(), testUser.getId());
		assertTrue(loan.getLoanAmount() == 1000);
		assertTrue(loan.getLoanTerm() == 6);
	}

	@Test
	public void testUserHasNotLoan() {
		assertFalse(testMoneyLoanService.hasMoneyLoans(testUser.getEmail()));
	}

	@Test(expected = NoSuchElementException.class)
	public void testUserGetLoanException() {
		testMoneyLoanService.getLoan(testUser.getEmail());
	}
	
	@Test
	public void testUserHasLoanException() {
		assertFalse(testMoneyLoanService.hasMoneyLoans(testUser.getEmail()));
	}

	public void testUserHasLoan() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		assertTrue(testMoneyLoanService.hasMoneyLoans(testUser.getEmail()));
	}

	public void testPayLoan() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		Loan loan = testMoneyLoanService.getLoan(testUser.getEmail());
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoan(loan);
		assertTrue(testUser.getAccount().getBalance() == 100);
		assertTrue(loan.getLoanTermsPayed() == 1);
	}

	public void testPayLoanAndFinish() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		Loan loan = testMoneyLoanService.getLoan(testUser.getEmail());
		ReflectionTestUtils.setField(loan, "loanTermsPayed", 5);
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoan(loan);
		assertTrue(testUser.getAccount().getBalance() == 100);
		assertEquals(testMoneyLoanService.getLoans().size(), 0);
	}

	public void testPayLoanNotMoney() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		Loan loan = testMoneyLoanService.getLoan(testUser.getEmail());
		testUser.getAccount().addMoney(100);
		testMoneyLoanService.payLoan(loan);
		assertEquals(loan.getCreditSituation(), CreditSituationType.RISK);
	}

	public void testPayLoanIsRisk() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		Loan loan = testMoneyLoanService.getLoan(testUser.getEmail());
		testUser.getAccount().addMoney(100);
		testMoneyLoanService.payLoan(loan);
		assertTrue(loan.isRisk());
	}

	public void testPayLoans() throws UnexistException {
		testMoneyLoanService.createLoan(testUser.getEmail());
		testUser.getAccount().addMoney(300);
		testMoneyLoanService.payLoans();
		assertTrue(testUser.getAccount().getBalance() == 100);
	}

	private Loan prepareLoanMock(Boolean loanIsRisk) {
		List<Loan> loans = new ArrayList<Loan>();
		Loan loan = mock(Loan.class);
		loans.add(loan);
		ReflectionTestUtils.setField(testMoneyLoanService, "loans", loans);
		User user = mock(User.class);
		when(loan.isRisk()).thenReturn(loanIsRisk);
		//when(user.getId()).thenReturn(1);
		when(loan.getEmail()).thenReturn(user.getEmail());
		return loan;
	}
	
}
