package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	Integer id;

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
    }

	public String getBirthDate() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return birthDate.format(formatter);
	}
	
	public boolean isDutiful() {
		//TODO: Chequear si en al menos 3 de los ultimos eventos a los cuales asistio, cumplio con su participacion
		return true;
	}
	
	public boolean hasMoneyLoans() {
		//TODO: Verificar si tiene un prestamo en curso.
		return true;
	}
	
	public void addEventAssist(Event event) {
		eventsAssisted.add(event);
	}
	
	public List<Event> getEventsInCourse() {
		return eventsAssisted.stream().filter(event -> event.getDate().isAfter(LocalDate.now().minusDays(1))).collect(Collectors.toList());
	}
	
	public List<Event> getEventsCoursed() {
		return eventsAssisted.stream().filter(event -> event.getDate().isBefore(LocalDate.now())).collect(Collectors.toList());
	}
	
}

