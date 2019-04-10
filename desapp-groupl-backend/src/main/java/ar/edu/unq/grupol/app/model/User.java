package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
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
	private LocalDate fechaNacimiento;
	
	public User(String nombre, String apellido, String email, LocalDate fechaNacimiento) throws InvalidParameterException {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;

	}
	
}

