package ar.edu.unq.groupl.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@Entity
@Table(name = "loans")
public class Loan {

	@Id
	private String email;

	private Integer loanAmount;
	private Integer monthlyPayback;
	private Integer loanTerm;
	private Integer loanTermsPayed;
	@Setter private CreditSituationType creditSituation;
	
	public Loan(String email, Integer loanAmount, Integer monthlyPayback, Integer loanTerm) {
		this.email = email;
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
		return loanTerm.equals(loanTermsPayed);
	}
	
	public boolean isRisk() {
		return creditSituation.equals(CreditSituationType.RISK);
	}
	
}