package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.EvictingQueue;

import ar.edu.unq.grupol.app.service.MoneyLoanService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private Integer id;

	private EvictingQueue<Boolean> dutifulList;

	private MoneyLoanService moneyLoanService;

	@NotNull(message = "Name must be defined")
	@Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
	private String name;

	@NotNull(message = "Lastname must be defined")
	@Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
	private String lastName;

	@NotNull(message = "Email must be defined")
	private String email;

	@NotNull(message = "Pasword must be defined")
	private String password;

	@NotNull(message = "Birthdate must be defined")
	private LocalDate birthDate;

	private List<Event> eventsAssisted;

	private Account account;

	public User() {
		eventsAssisted = new ArrayList<Event>();
		account = new Account();
		dutifulList = EvictingQueue.create(3);
		//IntStream.rangeClosed(1, 3).forEach(s -> dutifulList.add(true));
		dutifulList.add(true);
		dutifulList.add(true);
		dutifulList.add(true);
		
	}

	public String getBirthDate() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return birthDate.format(formatter);
	}

	public boolean isDutiful() {
		return !dutifulList.contains(false);
	}

	public boolean hasMoneyLoans() {
		return moneyLoanService.hasMoneyLoans(this);
	}

	public void addEventAssist(Event event) {
		eventsAssisted.add(event);
	}

	public List<Event> getEventsInCourse() {
		return eventsAssisted.stream().filter(event -> event.getDate().isAfter(LocalDate.now().minusDays(1)))
				.collect(Collectors.toList());
	}

	public List<Event> getEventsCoursed() {
		return eventsAssisted.stream().filter(event -> event.getDate().isBefore(LocalDate.now()))
				.collect(Collectors.toList());
	}

}
