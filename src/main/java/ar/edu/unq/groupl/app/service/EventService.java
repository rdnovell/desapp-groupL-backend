package ar.edu.unq.groupl.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Account;
import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.Validator;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.persistence.BasketRepository;
import ar.edu.unq.groupl.app.persistence.CrowdRepository;
import ar.edu.unq.groupl.app.persistence.ItemRepository;
import ar.edu.unq.groupl.app.persistence.PartyRepository;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnCreate;

@Component
public class EventService {
	
	@Autowired private EmailSender emailSender;
	@Autowired private ItemRepository itemRepository;
	@Autowired private PartyRepository partyRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private BasketRepository basketRepository; 
	@Autowired private CrowdRepository crowdRepository;
	
	private <T> T createEvent(Event event) throws InvalidParameterException {
		Validator.validateEvent(event);
		event.setEmailSender(emailSender);
		event.sendInvitations();
		return (T) event;
	}

	public void createParty(PartyDTOOnCreate partyDTO) throws InvalidParameterException {
		Optional<User> owner = userRepository.findById(partyDTO.getOwner());
		List<Item> items = itemRepository.findAllById(partyDTO.getItems());
		Party party = new Party();
		party.setTitle(partyDTO.getTitle());
		party.setOwner(owner.get());
		party.setItems(items);
		List<User> users = userRepository.findAllById(partyDTO.getGuests());
		List<Event> events = new ArrayList<Event>();
		events.add(party);
		users.forEach(eachUser -> eachUser.setGuestedEvents(events));
		party.setGuests(users);
		party.setDate(partyDTO.getDate());
		party.setExpirationDate(partyDTO.getExpirationDate());
		partyRepository.save(party);
		userRepository.saveAll(users);
//		Party event = createEvent(party);
//		partyRepository.save(event);
//		return event;
	}

	public Basket createBasket(Basket basket) throws InvalidParameterException {
		Basket event = createEvent(basket);
		basketRepository.save(event);
		return event;
	}
	
	public CrowdFundingCommonAccount createCrowdFundingCommonAccount(CrowdFundingCommonAccount crowdFundingCommonAccount) throws InvalidParameterException {
		crowdFundingCommonAccount.setCommonAccount(new Account());
		CrowdFundingCommonAccount event = createEvent(crowdFundingCommonAccount);
		//eventRepository.save(event);
		return event;
	}

	public CrowdFunding createCrowdFunding(CrowdFunding crowdFunding) throws InvalidParameterException {
		CrowdFunding event = createEvent(crowdFunding);
		crowdRepository.save(event);
		return event;
	}
	
	public void addFunds(CrowdFundingCommonAccount crowdFundingCommonAccount, User user, Integer amount) throws InvalidAmount {
		user.getAccount().getMoney(amount);
		crowdFundingCommonAccount.addFunds(amount);
	}

}