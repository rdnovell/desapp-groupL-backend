package ar.edu.unq.grupol.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.grupol.app.model.Account;
import ar.edu.unq.grupol.app.model.Basket;
import ar.edu.unq.grupol.app.model.CrowdFunding;
import ar.edu.unq.grupol.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.grupol.app.model.Event;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.model.Validator;
import ar.edu.unq.grupol.app.model.exception.InvalidAmount;
import ar.edu.unq.grupol.app.model.exception.InvalidParameterException;

@Component
public class EventService {
	
	@Autowired EmailSender emailSender;
	
	private <T> T createEvent(Event event) throws InvalidParameterException {
		Validator.validateEvent(event);
		event.setEmailSender(emailSender);
		event.sendInvitations();
		return (T) event;
	}

	public Party createParty(Party party) throws InvalidParameterException {
		return createEvent(party);
	}

	public CrowdFundingCommonAccount createCrowdFundingCommonAccount(CrowdFundingCommonAccount crowdFundingCommonAccount) throws InvalidParameterException {
		crowdFundingCommonAccount.setCommonAccount(new Account());
		return createEvent(crowdFundingCommonAccount);
//		crowdFundingCommonAccount.sendInvitations();
//		return crowdFundingCommonAccount;
	}

	public CrowdFunding createCrowdFunding(CrowdFunding crowdFunding) throws InvalidParameterException {
		return createEvent(crowdFunding);
//		crowdFunding.sendInvitations();
//		return crowdFunding;
	}
	
	public void addFunds(CrowdFundingCommonAccount crowdFundingCommonAccount, User user, Integer amount) throws InvalidAmount {
		user.getAccount().getMoney(amount);
		crowdFundingCommonAccount.addFunds(amount);
	}

	public Basket createBasket(Basket basket) throws InvalidParameterException {
		return createEvent(basket);
//		basket.sendInvitations();
//		return basket;
	}

}
