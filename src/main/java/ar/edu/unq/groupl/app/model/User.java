package ar.edu.unq.groupl.app.model;

import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.MoneyLoanService;
import ar.edu.unq.groupl.app.service.validation.Email;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@JsonIgnore
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_dutifuls")
	private List<Boolean> dutifulList;

	@JsonIgnore
	@Transient
	private MoneyLoanService moneyLoanService;

	@NotNull(message = "Name must be defined")
	@Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
	private String name;

	@NotNull(message = "Lastname must be defined")
	@Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
	private String lastName;

	@Id
	@NotNull(message = "Email must be defined")
	@Email(message = "Email must be valid")
	private String email;
	
	@OneToMany(
	        mappedBy = "owner",
	        cascade = CascadeType.ALL
	)
	private List<Event> eventsOwner;

	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "assisted_and_events",
	        joinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email"),
	        inverseJoinColumns = @JoinColumn(name="event_id", referencedColumnName = "id")
	)
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Event> eventsAssisted;

	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "guests_and_events",
	        joinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email"),
	        inverseJoinColumns = @JoinColumn(name="event_id", referencedColumnName = "id")
	)
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Event> guestedEvents;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private Account account;

	public User() {
		eventsAssisted = new ArrayList<Event>();
		guestedEvents = new ArrayList<Event>();
		account = new Account(this);
		dutifulList = new ArrayList<Boolean>(Arrays.asList(true, true, true));
	}

	public boolean isDutiful() {
		return !dutifulList.contains(false);
	}

	public void addDutiful(boolean value) {
		IntStream.rangeClosed(0, 1).forEach(index -> dutifulList.set(index, dutifulList.get(index + 1)));
		dutifulList.set(dutifulList.size() - 1, value);
	}

	public boolean hasMoneyLoans() {
		return moneyLoanService.hasMoneyLoans(email);
	}

	public void addEventAssist(Event event) {
		guestedEvents.removeIf(guestedEvent -> guestedEvent.getId().equals(event.getId()));
		eventsAssisted.add(event);
	}
	
	public void addEventGuest(Event event) {
		guestedEvents.add(event);
	}
	
	public void removeEventGuest(Event event) {
		guestedEvents.removeIf(guestedEvent -> guestedEvent.getId().equals(event.getId()));
	}

	private List<Event> filterEventsBy(Predicate<Event> functionToFilter) {
		return ListUtil.toList(eventsAssisted.stream().filter(functionToFilter));
	}

	public List<Event> getEventsInCourse() {
		return filterEventsBy(this::eventInCourse);
	}

	public List<Event> getEventsCoursed() {
		return filterEventsBy(this::eventCoursed);
	}

	private boolean eventCoursed(Event event) {
		return compareEventsTime(event, date -> date.isBefore(LocalDate.now()));
	}

	private boolean eventInCourse(Event event) {
		return compareEventsTime(event, date -> date.isAfter(LocalDate.now().minusDays(1)));
	}

	private boolean compareEventsTime(Event event, Function<LocalDate, Boolean> compareFunction) {
		return compareFunction.apply(event.getDate());
	}

	@Override
	public boolean equals(Object object) {
		if (User.class == object.getClass()) {
			User user = (User) object;
			return email.equals(user.getEmail());
		}
		return false;
	}

}