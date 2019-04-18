package ar.edu.unq.grupol.app.model;

import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CrowdFundingCommonAccount extends Event {

	private Account commonAccount;
	
	public void addFunds(Integer amount) {
		commonAccount.addMoney(amount);
	}
	
	public void getFunds(Integer amount) throws InvalidAmount {
		commonAccount.getMoney(amount);
	}
	
	public Integer getBalance() {
		return commonAccount.getBalance();
	}
	
}
