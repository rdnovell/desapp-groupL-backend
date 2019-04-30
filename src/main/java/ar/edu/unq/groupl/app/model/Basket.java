package ar.edu.unq.groupl.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class Basket extends Event {

	@Getter
	private List<ItemAssigned> itemsAssigned = new ArrayList<ItemAssigned>();

	@Override
	public void addItems(List<Item> items) {
		super.addItems(items);
		items.forEach(item -> itemsAssigned.add(new ItemAssigned(null, item)));
	}

	public void assignItem(User user, Item item) {
		Optional<ItemAssigned> itemAssigned = itemsAssigned.stream().filter(itemFitered -> itemFitered.getItem().getId() == item.getId()).findFirst();
		if (itemAssigned.isPresent() && itemAssigned.get().getUser() == null) {
			itemAssigned.get().setUser(user);
		}
	}

}
