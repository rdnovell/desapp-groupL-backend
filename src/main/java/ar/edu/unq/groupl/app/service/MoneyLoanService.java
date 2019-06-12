package ar.edu.unq.groupl.app.service;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.CreditSituationType;
import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

@Component
@Getter
public class MoneyLoanService implements Observer {

	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(MoneyLoanService.class);

	private List<Loan> loans = new ArrayList<Loan>();

	public void createLoan(String email) throws UnexistException{
		if (isDutiful(email) && !hasMoneyLoans(email)) {
			loans.add(new Loan(email, 1000, 200, 6));
		}
	}

	private boolean isDutiful(String email) throws UnexistException {
		return userService.getUser(email).isDutiful();
	}

	public boolean hasMoneyLoans(String email) {
		return loans.stream().anyMatch(loan -> loan.getEmail().equals(email));
	}

	public Loan getLoan(User user) {
		return loans.stream().filter(loan -> loan.getEmail().equals(user.getEmail())).findFirst().get();
	}

	public List<Loan> getAllUserLoans(User user) {
		return loans.stream().filter(loan -> loan.getEmail().equals(user.getEmail())).collect(Collectors.toList());
	}

	//Cron works on 4am every day 5 of month.
	@Scheduled(cron = "0 0 4 5 * ?")
	public void payLoans() {
		logger.info("Se realizó el debito automatico.");
		loans.forEach(loan -> payLoan(loan));
	}

	@SneakyThrows
	public void payLoan(Loan loan) {
		User user = userService.getUser(loan.getEmail());
		Account account = user.getAccount();
		try {
			account.getMoney(loan.getMonthlyPayback());
			loan.updateLoanTermsPayed();
			loan.setCreditSituation(CreditSituationType.NORMAL);
			if (loan.isFinished()) {
				loans.remove(loan);
			}
		} catch (InvalidAmount e) {
			loan.setCreditSituation(CreditSituationType.RISK);
		}
	}

	@Override
	public void update(Observable obs, Object _user) {
		User user = (User) _user; 
		Loan loan = getLoan(user);
		if (loan.isRisk()) {
			payLoan(loan);
		}
	}

	public boolean loanAvailable(String email) throws UnexistException {
		return isDutiful(email) && !hasMoneyLoans(email);
	}
}