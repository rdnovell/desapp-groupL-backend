package ar.edu.unq.groupl.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.CreditSituationType;
import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import lombok.Getter;

@Component
@Getter
public class MoneyLoanService implements Observer {
	
	private Logger logger = LoggerFactory.getLogger(MoneyLoanService.class);

	private List<Loan> loans = new ArrayList<Loan>();

	public void createLoan(User user) {
		if (user.isDutiful() && !hasMoneyLoans(user)) {
			loans.add(new Loan(user, 1000, 200, 6));
		}
	}

	public boolean hasMoneyLoans(User user) {
		return loans.stream().anyMatch(loan -> loan.getUser().getId() == user.getId());
	}

	public Loan getLoan(User user) {
		return loans.stream().filter(loan -> loan.getUser().getId() == user.getId()).findFirst().get();
	}

	@Scheduled(cron = "0 30 20 5 * ?")
	public void payLoans() {
		logger.info("Se realizó el debito automatico.");
		loans.forEach(loan -> payLoan(loan));
	}

	public void payLoan(Loan loan) {
		Account account = loan.getUser().getAccount();
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

}