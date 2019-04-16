package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.grupol.app.model.*;

public class TestBuilder {

	// Herramientas para testear users
	public static class UserBuilder {
		private User testUser = new User();		

		public UserBuilder validUser(){
			testUser.setNombre("Pedro");
			testUser.setApellido("Esposito");
	     	testUser.setEmail("pedro.esposito@gmail.com");
	     	testUser.setPassword("alpa1234");
	     	testUser.setFechaNacimiento(LocalDate.of(1985, Month.JANUARY, 1));
			return this;
		}
		
		public User build() {
			return testUser;
		}
	}
	
	public static UserBuilder testUser() {
		return new UserBuilder();
	}

	// Herramientas para testear eventos

	public static class FiestaBuilder {

		private Fiesta testFiesta = new Fiesta();
		private User organizador = TestBuilder.testUser().validUser().build();

		public FiestaBuilder validFiesta(){
			testFiesta.setTitulo("Asado con amigos");
			testFiesta.setOrganizador(organizador);

			List<Item> items = new ArrayList<Item>();
			items.add(new Item("Asado",100));
			testFiesta.setItems(items);

            List<User> invitados = new ArrayList<User>();
            invitados.add(testUser().validUser().build());
            testFiesta.setInvitados(invitados);

            return this;
		}

		public Fiesta build() {
			return testFiesta;
		}
    }

	public static FiestaBuilder testEvento() {
		return new FiestaBuilder();
	}
}
