package ar.edu.unq.grupol.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Event {
	private String title;
	private User owner;
	private List<User> guests;
	@Setter(AccessLevel.NONE)
	private List<User> confirmedGuests = new ArrayList<User>();
	private List<Item> items;

	public void addConfirmedGuests(User user){
		confirmedGuests.add(user);
	}

    public void enviarInvitaciones(){
    	guests.stream().forEach(guest -> EmailSender.send(title,guest.getEmail()));
    }
}
