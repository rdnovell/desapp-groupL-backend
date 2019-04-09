package ar.edu.unq.grupol.app.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private String nombre;
	private String apellido;
	private String email;
	private LocalDate fechaNacimiento;
}

