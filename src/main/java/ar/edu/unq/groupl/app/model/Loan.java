package ar.edu.unq.groupl.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String email;
	private Integer loanAmount;
	private Integer monthlyPayback;
	private Integer loanTerm;
	private Integer loanTermsPayed;
	@Setter private CreditSituationType creditSituation;
    private ZonedDateTime date;
	
	public Loan(String email, Integer loanAmount, Integer monthlyPayback, Integer loanTerm) {
		this.email = email;
		this.loanAmount = loanAmount;
		this.monthlyPayback = monthlyPayback;
		this.loanTerm = loanTerm;
		this.loanTermsPayed = 0;
		this.creditSituation = CreditSituationType.NORMAL;
		this.date = ZonedDateTime.now();
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