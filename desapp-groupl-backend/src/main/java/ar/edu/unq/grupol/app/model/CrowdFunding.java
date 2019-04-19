package ar.edu.unq.grupol.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

public class CrowdFunding extends Event {

	@Getter
	private List<Purchase> itemPurchases = new ArrayList<Purchase>();
	
	public Integer getCostPerUser() {
		return getTotalCost() / getConfirmedGuests().size();
	}
	
	public Integer getCost(User user) {
		Integer value = 0;
		Optional<Purchase> purchaseFounded = itemPurchases.stream().filter(purchase -> purchase.getUser().getId() == user.getId()).findFirst();
		if(purchaseFounded.isPresent()) {
			value = getCostPerUser() - purchaseFounded.get().getAmount() ;
		} else {
			value = getCostPerUser();
		}
		return value;
	}
	
	public void addItemPurchase(User user, Integer amount) {
		if (userIsConfimated(user)) {
			addAmountPurchase(user, amount);
		}
	}
	
	private void addAmountPurchase(User user, Integer amount) {
		Optional<Purchase> purchaseFounded = itemPurchases.stream().filter(purchase -> purchase.getUser().getId() == user.getId()).findFirst();
		if(purchaseFounded.isPresent()) {
			purchaseFounded.get().addAmount(amount);
		} else {
			itemPurchases.add(new Purchase(user, amount));
		}
	}
}
