package ar.edu.unq.groupl.app.service;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.CreditSituationType;
import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.persistence.LoanRepository;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Component
@Getter
public class MoneyLoanService implements Observer {

	@Autowired
	private UserService userService;
	@Autowired private LoanRepository loanRepository;

	private Logger logger = LoggerFactory.getLogger(MoneyLoanService.class);

	public void createLoan(String email) throws UnexistException{
		if (isDutiful(email) && !hasMoneyLoans(email)) {
			//loans.add(new Loan(email, 1000, 200, 6));
			Loan loan = new Loan(email, 1000, 200, 6);
			loanRepository.save(loan);
		}
	}

	private boolean isDutiful(String email) throws UnexistException {
		return userService.getUser(email).isDutiful();
	}

	public boolean hasMoneyLoans(String email) {
		return getAllUserLoans(email).stream().anyMatch(loan -> !loan.isFinished());
	}

	public Loan getLoan(String email) {
		return getAllUserLoans(email).stream().filter(loan -> !loan.isFinished()).findFirst().get();
	}


	//Cron works on 4am every day 5 of month.
	@Scheduled(cron = "0 0 4 5 * ?")
	public void payLoans() {
		logger.info("Se realizó el debito automatico.");
		getLoans().forEach(loan -> payLoan(loan));
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
				//loans.remove(loan);
			}
		} catch (InvalidAmount e) {
			loan.setCreditSituation(CreditSituationType.RISK);
		}
	}

	@Override
	public void update(Observable obs, Object _user) {
		User user = (User) _user; 
		Loan loan = getLoan(user.getEmail());
		if (loan.isRisk()) {
			payLoan(loan);
		}
	}

	public boolean loanAvailable(String email) throws UnexistException {
		return isDutiful(email) && !hasMoneyLoans(email);
	}

	public List<Loan> getAllUserLoans(String email) {
		return loanRepository.getUserLoans(email);
	}

	public List<Loan> getLoans() {
		return loanRepository.findAll();
	}
}