package ar.edu.unq.groupl.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.GuestNotFoundException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.EmailSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@Transient
	private EmailSender emailSender;

	private String title;

	@OneToOne
	private User owner;

	/*@ManyToMany
	@JoinTable(name = "event_guest", 
	           joinColumns = {	@JoinColumn(name = "event_id", referencedColumnName = "id") }, 
	           inverseJoinColumns = { @JoinColumn(name = "guest_id", referencedColumnName = "email") })*/
    @Column
	@ElementCollection(targetClass=User.class)
	private List<User> guests = new ArrayList<User>();

	@Setter(AccessLevel.NONE)
    @Column
	@ElementCollection(targetClass=User.class)
	private List<User> confirmedGuests = new ArrayList<User>();

    @Column
	@ElementCollection(targetClass=Item.class)
	private List<Item> items = new ArrayList<Item>();
    
	private LocalDate date;

	private boolean checkGuest(List<User> users, User user) {
		return users.stream().anyMatch(user::equals);
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

	public void sendInvitations() {
		if (!guests.isEmpty()) {
			List<MailJetUser> mailJetUsers = ListUtil.toList(guests.stream().map(this::createMailJetUser));
			emailSender.send(mailJetUsers);
		}
	}

	private MailJetUser createMailJetUser(User user) {
		return new MailJetUser(user.getName() + " " + user.getLastName(), user.getEmail());
	}

	public void addItems(List<Item> items) {
		this.items.addAll(items);
	}

	public Integer getTotalCost() {
		return items.stream().mapToInt(item -> item.getPrice()).sum();
	}

	public void withTemplate(Template template) {
		addItems(template.getItems());
	}

	public void setNotDutiful(User user) {
		user.addDutiful(false);
	}

}
