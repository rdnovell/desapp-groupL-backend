package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class User {

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

	public String getBirthDate() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return birthDate.format(formatter);
		
	}
	
}

