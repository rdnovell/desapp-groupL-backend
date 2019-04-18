package ar.edu.unq.grupol.app.service;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.grupol.app.model.Event;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.Validator;

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
	
	public void addFunds(CrowdFundingCommonAccount crowdFundingCommonAccount, Integer amount) {
		crowdFundingCommonAccount.addFunds(amount);
	}

	public Event createEvent(Event event) {
		event.sendInvitations();
		return event;
	}
	
}
