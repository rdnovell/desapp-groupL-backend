package ar.edu.unq.grupol.app.model;

import java.util.List;
import java.util.Optional;

public class Basket extends Event {
	
	private List<ItemAssigned> itemsAssigned;
	
	@Override
	public void addItems(List<Item> items) {
    	super.addItems(items);
    	items.forEach(item -> itemsAssigned.add(new ItemAssigned(null, item)));
    }
	
	public void assignItem(User user, Item item) {
		Optional<ItemAssigned> itemAssigned = itemsAssigned.stream().filter(itemFitered -> itemFitered.getItem().getId() == item.getId()).findFirst();
		if (itemAssigned.isPresent()) {
			if (itemAssigned.get().getUser() == null) {
				itemAssigned.get().setUser(user);
			}
		}
	}

}
