package modelTest;

import java.time.LocalDate;
import java.time.Month;

import ar.edu.unq.grupol.app.model.User;

public class TestBuilder {

	public static class UserBuilder {
		private User testUser = new User();		
		
			
		public UserBuilder validUser(){
			testUser.setNombre("Pedro");
			testUser.setApellido("Esposito");
	     	testUser.setEmail("pedro.esposito@gmail.com");
	     	testUser.setFechaNacimiento(LocalDate.of(1985, Month.JANUARY, 1));;
			return this;
		}
		
		public User build() {
			return testUser;
		}
	}
	
	public static UserBuilder testUser() {
		return new UserBuilder();
	}
	
}
