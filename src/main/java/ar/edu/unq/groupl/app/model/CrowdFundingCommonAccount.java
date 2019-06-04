package ar.edu.unq.groupl.app.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "crowd_fundings_common_accounts")
public class CrowdFundingCommonAccount extends Event {

	@OneToOne()
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