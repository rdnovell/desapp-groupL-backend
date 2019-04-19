package ar.edu.unq.grupol.app.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Loan {

	private User user;
	private Integer money;
	private Integer loanTerm;
	private Integer loanTermsPayed;
	private CreditSituationType creditSituation;
	
}
