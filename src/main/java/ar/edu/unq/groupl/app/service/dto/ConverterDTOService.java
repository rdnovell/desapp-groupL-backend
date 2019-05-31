package ar.edu.unq.groupl.app.service.dto;

import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.ItemAssigned;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.persistence.ItemRepository;
import ar.edu.unq.groupl.app.persistence.UserRepository;

@Component
public class ConverterDTOService {

	@Autowired private UserRepository userRepository;
	@Autowired private ItemRepository itemRepository;
	
	public Party converter(PartyDTO partyDTO) {
		Party party = new Party();
		party.setTitle(partyDTO.getTitle());
		User owner = getUser(partyDTO.getOwner());
		List<User> guests = map(partyDTO.getGuests(), this::getUser);
		List<Item> items = map(partyDTO.getItems(), this::getItem);
		party.setOwner(owner);
		party.setItems(items);
		//party.setGuests(guests);
		party.setDate(partyDTO.getDate());
		party.setExpirationDate(partyDTO.getExpirationDate());
		return party;
	}
	
	public Basket converter(BasketDTO basketDTO) {
		Basket basket = new Basket();
		basket.setTitle(basketDTO.getTitle());
		User owner = getUser(basketDTO.getOwner());
		List<User> guests = map(basketDTO.getGuests(), this::getUser);
		List<Item> items = map(basketDTO.getItems(), this::getItem);
		basket.setOwner(owner);
		basket.setItems(items);
		//basket.setGuests(guests);
		basket.setDate(basketDTO.getDate());
		List<ItemAssigned> itemsAssigned = map(basketDTO.getItemsAssigned(), this::getItemAssigned);
		basket.setItemsAssigned(itemsAssigned);
		return basket;
	}

	public CrowdFunding converter(CrowdDTO crowdDTO) {
		CrowdFunding crowd = new CrowdFunding();
		crowd.setTitle(crowdDTO.getTitle());
		User owner = getUser(crowdDTO.getOwner());
		List<User> guests = map(crowdDTO.getGuests(), this::getUser);
		List<Item> items = map(crowdDTO.getItems(), this::getItem);
		crowd.setOwner(owner);
		crowd.setItems(items);
		//crowd.setGuests(guests);
		crowd.setDate(crowdDTO.getDate());
		return crowd;
	}
	
	
	public UserDTO convertUserToDTO(User user) {
		return new UserDTO(user);
	}
	
	private <T, R> List<R> map(List<T> list, Function<T, R> functionMap) {
		return ListUtil.toList(list.stream().map(functionMap));
	}
	
	private User getUser(String email) {
		return userRepository.findById(email).get();
	}
	
	private Item getItem(Integer id) {
		return itemRepository.findById(id).get();
	}

	private ItemAssigned getItemAssigned(ItemAssignedDTO itemAssignedDTO) {
		ItemAssigned itemAssigned = new ItemAssigned();
		itemAssigned.setUser(getUser(itemAssignedDTO.getUser()));
		itemAssigned.setItem(getItem(itemAssignedDTO.getItem()));
		return null;
	}

}