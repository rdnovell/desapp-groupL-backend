package ar.edu.unq.grupol.app.service;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.Basket;
import ar.edu.unq.grupol.app.model.CrowdFunding;
import ar.edu.unq.grupol.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.grupol.app.model.Event;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.Validator;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;

public class EventService {

	public Party createParty(Party party) throws InvalidParameterException {
		Validator.validateParty(party);
		party.sendInvitations();
		return party;
	}

	public CrowdFundingCommonAccount createCrowdFundingCommonAccount(CrowdFundingCommonAccount crowdFundingCommonAccount) {
		crowdFundingCommonAccount.setCommonAccount(new Account());
		crowdFundingCommonAccount.sendInvitations();
		return crowdFundingCommonAccount;
	}

	public CrowdFunding createCrowdFunding(CrowdFunding crowdFunding) {
		crowdFunding.sendInvitations();
		return crowdFunding;
	}
	
	public void addFunds(CrowdFundingCommonAccount crowdFundingCommonAccount, User user, Integer amount) throws InvalidAmount {
		user.getAccount().getMoney(amount);
		crowdFundingCommonAccount.addFunds(amount);
	}

	public Basket createBasket(Basket basket) {
		basket.sendInvitations();
		return basket;
	}

}
