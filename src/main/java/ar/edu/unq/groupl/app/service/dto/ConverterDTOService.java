package ar.edu.unq.groupl.app.service.dto;

import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Item;
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
		party.setGuests(guests);
		party.setDate(partyDTO.getDate());
		party.setExpirationDate(partyDTO.getExpirationDate());
		return party;
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

}