package ar.edu.unq.grupol.app.service;

import java.util.List;

import ar.edu.unq.grupol.app.model.Loan;
import ar.edu.unq.grupol.app.model.User;

public class MoneyLoanService {

	public void createLoan(User user) {
		//TODO: Crea un prestamo si se puede para el usuario.
		// Si es cumplidor se le puede asignar 1000.
		// En 6 cuotas mensuales de 200.
	}
	
	public List<Loan> getAllLoans() {
		//TODO: Retornar el listado de creditos en curso y la situacion.
		// Usuario, cuota y morosidad.
		return null;
	}
	
}
