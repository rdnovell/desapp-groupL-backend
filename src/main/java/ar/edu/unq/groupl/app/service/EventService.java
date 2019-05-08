package ar.edu.unq.groupl.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.Validator;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.BasketRepository;
import ar.edu.unq.groupl.app.persistence.PartyRepository;

@Component
public class EventService {
	
	@Autowired private EmailSender emailSender;
	@Autowired private PartyRepository partyRepository; 
	@Autowired private BasketRepository basketRepository; 
	
	private <T> T createEvent(Event event) throws InvalidParameterException {
		Validator.validateEvent(event);
		event.setEmailSender(emailSender);
		event.sendInvitations();
		return (T) event;
	}

	public Party createParty(Party party) throws InvalidParameterException {
		Party event = createEvent(party);
		partyRepository.save(event);
		return event;
	}

	public CrowdFundingCommonAccount createCrowdFundingCommonAccount(CrowdFundingCommonAccount crowdFundingCommonAccount) throws InvalidParameterException {
		crowdFundingCommonAccount.setCommonAccount(new Account());
		CrowdFundingCommonAccount event = createEvent(crowdFundingCommonAccount);
		//eventRepository.save(event);
		return createEvent(event);
	}

	public CrowdFunding createCrowdFunding(CrowdFunding crowdFunding) throws InvalidParameterException {
		CrowdFunding event = createEvent(crowdFunding);
		//eventRepository.save(event);
		return event;
	}
	
	public void addFunds(CrowdFundingCommonAccount crowdFundingCommonAccount, User user, Integer amount) throws InvalidAmount {
		user.getAccount().getMoney(amount);
		crowdFundingCommonAccount.addFunds(amount);
	}

	public Basket createBasket(Basket basket) throws InvalidParameterException {
		Basket event = createEvent(basket);
		basketRepository.save(event);
		return event;
	}

}