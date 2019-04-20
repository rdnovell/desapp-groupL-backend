package ar.edu.unq.grupol.app.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.grupol.app.model.Item;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.PartyDTO;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.persistence.ItemRepository;
import ar.edu.unq.grupol.app.persistence.UserRepository;

@Component
public class ConverterDTOService {

	@Autowired private UserRepository userRepository;
	@Autowired private ItemRepository itemRepository;
	
	public Party converter(PartyDTO partyDTO) {
		Party party = new Party();
		party.setTitle(partyDTO.getTitle());
		User owner = userRepository.get(partyDTO.getOwner());
		List<User> guests = partyDTO.getGuests().stream().map(userRepository::get).collect(Collectors.toList());
		List<Item> items = partyDTO.getItems().stream().map(itemRepository::get).collect(Collectors.toList());
		party.setOwner(owner);
		party.setItems(items);
		party.setGuests(guests);
		party.setDate(partyDTO.getDate());
		party.setExpirationDate(partyDTO.getExpirationDate());
		return party;
	}

}
