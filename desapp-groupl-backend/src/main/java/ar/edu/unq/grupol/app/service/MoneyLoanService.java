package ar.edu.unq.grupol.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.stereotype.Component;

import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.CreditSituationType;
import ar.edu.unq.grupol.app.model.Loan;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import lombok.Getter;

@Component
@Getter
public class MoneyLoanService implements Observer {

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

	public void payLoans() {
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
