package ar.edu.unq.groupl.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.persistence.ItemRepository;

@Component
public class ItemService {
	
	@Autowired private ItemRepository itemRepository;
	
	public List<Item> getItems() {
		return itemRepository.findAll();
	}

	public void createItem(Item item) {
		if (!itemRepository.existsById(item.getTitle())) {
			itemRepository.save(item);
		}
	}

}
