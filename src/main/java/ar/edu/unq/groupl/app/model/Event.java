package ar.edu.unq.groupl.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.GuestNotFoundException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.EmailSender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@Transient
	private EmailSender emailSender;

	private String title;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne()
    @JoinColumn(name = "owner_email")
	private User owner;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "guestedEvents")
	private List<User> guests = new ArrayList<User>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "eventsAssisted")
	private List<User> confirmedGuests = new ArrayList<User>();

    @Column
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable
    @ManyToMany
	private List<Item> items = new ArrayList<Item>();
    
	private LocalDate date;

	private boolean checkGuest(List<User> users, User user) {
		return users.stream().anyMatch(user::equals);
	}

	public boolean userIsConfirmated(User user) {
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
