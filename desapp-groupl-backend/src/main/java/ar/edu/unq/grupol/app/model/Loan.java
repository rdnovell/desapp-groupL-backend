package ar.edu.unq.grupol.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Loan {

	private User user;
	private Integer loanAmount;
	private Integer monthlyPayback;
	private Integer loanTerm;
	private Integer loanTermsPayed;
	@Setter private CreditSituationType creditSituation;
	
	public Loan(User user, Integer loanAmount, Integer monthlyPayback, Integer loanTerm) {
		this.user = user;
		this.loanAmount = loanAmount;
		this.monthlyPayback = monthlyPayback;
		this.loanTerm = loanTerm;
		this.loanTermsPayed = 0;
		this.creditSituation = CreditSituationType.NORMAL;
	}
	
	public void updateLoanTermsPayed() {
		loanTermsPayed++;
	}
	
	public boolean isFinished() {
		return loanTerm == loanTermsPayed;
	}
	
	public boolean isRisk() {
		return creditSituation == CreditSituationType.RISK;
	}
	
}
