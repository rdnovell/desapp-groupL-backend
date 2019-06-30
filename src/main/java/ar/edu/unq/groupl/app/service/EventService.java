package ar.edu.unq.groupl.app.service;

import ar.edu.unq.groupl.app.model.*;
import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.persistence.*;
import ar.edu.unq.groupl.app.service.annotation.Log;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnCreate;
import ar.edu.unq.groupl.app.service.dto.UserConfirmatedDTO;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventService {
	
	@Autowired private EmailSender emailSender;
	@Autowired private ItemRepository itemRepository;
	@Autowired private PartyRepository partyRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private BasketRepository basketRepository; 
	@Autowired private CrowdRepository crowdRepository;
	@Autowired private EventTransactionRepository eventTransactionRepository;
	
	public <T> T createEvent(Event event) throws InvalidParameterException {
		Validator.validateEvent(event);
		event.setEmailSender(emailSender);
		event.sendInvitations();
		return (T) event;
	}

	@Transactional
	public void createParty(PartyDTOOnCreate partyDTO) throws InvalidParameterException {
		Optional<User> owner = userRepository.findById(partyDTO.getOwner());
		List<Item> items = itemRepository.findAllById(partyDTO.getItems());
		Party party = new Party();
		party.setTitle(partyDTO.getTitle());
		party.setOwner(owner.get());
		party.setItems(items);
		List<User> users = userRepository.findAllById(partyDTO.getGuests());
		users.forEach(eachUser -> eachUser.addEventGuest(party));
		party.setGuests(users);
		party.setDate(partyDTO.getDate());
		party.setExpirationDate(partyDTO.getExpirationDate());
		partyRepository.save(party);
		party.setEmailSender(emailSender);
		party.sendInvitations();
	}
	
	@Transactional
	public void removeParty(Integer partyId) {
		partyRepository.removeById(partyId);
		eventTransactionRepository.removeById(partyId);
	}
	
	@Transactional
	public void updateItemsFromParty(Integer partyId, List<String> itemsTitle) {
		List<Item> items = itemRepository.findAllById(itemsTitle);
		Party party = partyRepository.findById(partyId).get();
		party.setItems(items);
		partyRepository.save(party);
	}
	
	@Transactional
	public void updateGuestsFromParty(Integer eventId, List<String> guests) {
		Party party = partyRepository.findById(eventId).get();
		List<String> guestsEmails = ListUtil.toList(party.getGuests().stream().map(guest -> guest.getEmail()));
		List<String> newEmails = ListUtils.removeAll(guests, guestsEmails);
		List<User> users = userRepository.findAllById(guests);
		List<User> usersToDelete = ListUtil.toList(party.getGuests().stream().filter(oldUser -> !users.contains(oldUser)));
		usersToDelete.forEach(user -> user.removeEventGuest(party));
		List<User> newUsers = ListUtil.toList(users.stream().filter(user -> newEmails.contains(user.getEmail())));
		newUsers.forEach(user -> user.addEventGuest(party));
		partyRepository.save(party);
		party.setEmailSender(emailSender);
		party.sendInvitations(newUsers);
	}

	public Basket createBasket(Basket basket) throws InvalidParameterException {
		Basket event = createEvent(basket);
		basketRepository.save(event);
		return event;
	}
	
	public CrowdFundingCommonAccount createCrowdFundingCommonAccount(CrowdFundingCommonAccount crowdFundingCommonAccount) throws InvalidParameterException {
		crowdFundingCommonAccount.setCommonAccount(new Account());
		CrowdFundingCommonAccount event = createEvent(crowdFundingCommonAccount);
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

	@Transactional
	public void confirmAssistance(UserConfirmatedDTO userConfirmatedDTO) throws EventException {
		Optional<Party> partyOptional = partyRepository.findById(userConfirmatedDTO.getEventId());
		Optional<User> userOptional = userRepository.findById(userConfirmatedDTO.getUserEmail());
		Party party = partyOptional.get();
		User user = userOptional.get();
		party.addConfirmedGuests(user);
		party.addEventTransactions(new EventTransaction(user, party));
		partyRepository.save(party);
	}

	@Log
	public List<Event> getTopEvents() {
		List<Event> events = new ArrayList<>();
		events.addAll(partyRepository.getTopEvents());
		return events;
	}
	
}