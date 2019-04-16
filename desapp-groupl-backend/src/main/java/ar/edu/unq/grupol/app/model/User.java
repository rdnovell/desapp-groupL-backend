package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private LocalDate fechaNacimiento;
	
	public String getFechaNacimiento() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return fechaNacimiento.format(formatter);
		
	}
	
}

