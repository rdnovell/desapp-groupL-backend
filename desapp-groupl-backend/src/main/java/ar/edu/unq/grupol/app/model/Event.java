package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import ar.edu.unq.grupol.app.model.exception.EventException;
import ar.edu.unq.grupol.app.model.exception.GuestNotFoundException;
import ar.edu.unq.grupol.app.service.EmailSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Event {
	private String title;
	private User owner;
	private List<User> guests;
	@Setter(AccessLevel.NONE)
	private List<User> confirmedGuests = new ArrayList<User>();
	private List<Item> items = new ArrayList<Item>();
	private LocalDate date;
	
	private boolean checkGuest(List<User> users, User user) {
		return users.stream().anyMatch(guest -> guest.getId() == user.getId());
	}
	
	public boolean userIsConfimated(User user) {
		return checkGuest(confirmedGuests, user);
	}

	public void addConfirmedGuests(User user) throws EventException {
		if (checkGuest(guests, user)) {
			confirmedGuests.add(user);
			user.addEventAssist(this);
		} else {
			throw new GuestNotFoundException("User must be invited to the event.");
		}
	}

    public void sendInvitations(){
    	guests.stream().forEach(guest -> {
			try {
				EmailSender.getInstance().send(title,guest.getEmail());
			} catch (SendFailedException e) {
			}
		});
    }
    
    public void addItems(List<Item> items) {
    	this.items.addAll(items);
    }
    
    public Integer getTotalCost() {
    	return items.stream().mapToInt(item -> item.getValue()).sum();
    }
    
    public void withTemplate(Template template) {
    	addItems(template.getItems());
    }
    
}
