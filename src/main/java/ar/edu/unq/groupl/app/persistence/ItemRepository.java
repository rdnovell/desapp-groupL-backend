package ar.edu.unq.groupl.app.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Item;

@Component
public class ItemRepository {
	
	private List<Item> items = new ArrayList<Item>(Arrays.asList(new Item(0, "Carbon", 100)));
	private int index = 0;
	
	public void save(Item item) {
		item.setId(index);
		items.add(item);
		index++;
	}
	
	public Item get(Integer id) {
		return items.get(id);
	}

}