package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.CrowdFundingCommonAccount;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.service.MoneyLoanService;

public class TestBuilder {

	public static UserBuilder testUser() {
		return new UserBuilder();
	}

	public static BasketBuilder testBasket() {
		return new BasketBuilder();
	}

	public static FiestaBuilder testParty() {
		return new FiestaBuilder();
	}
	
	public static CrowdFundingCommonAccountBuilder testCrowdFundingCommonAccount() {
		return new CrowdFundingCommonAccountBuilder();
	}

	public static CrowdFundingBuilder testCrowdFunding() {
		return new CrowdFundingBuilder();
	}
	
	public static class UserBuilder {
		private User testUser = new User();

		public UserBuilder validUser() {
			testUser.setMoneyLoanService(new MoneyLoanService());
			testUser.setName("Pedro");
			testUser.setLastName("Esposito");
			testUser.setEmail("rubendario.novelli@gmail.com");
			return this;
		}

		public User build() {
			return testUser;
		}
	}

	public static class FiestaBuilder {

		private Party testFiesta = new Party();
		private User organizador = TestBuilder.testUser().validUser().build();

		public FiestaBuilder validFiesta() {
			testFiesta.setTitle("Asado con amigos");
			testFiesta.setOwner(organizador);

			List<Item> items = new ArrayList<Item>();
			items.add(new Item("Asado", 100));
			testFiesta.setItems(items);

			testFiesta.setExpirationDate(LocalDate.now().plusDays(10));
			testFiesta.setDate(LocalDate.now().plusDays(12));

			List<User> invitados = new ArrayList<User>();
			invitados.add(TestBuilder.testUser().validUser().build());
			testFiesta.setGuests(invitados);

			return this;
		}

		public Party build() {
			return testFiesta;
		}
	}

	public static class BasketBuilder {

		private Basket testBasket = new Basket();
		private User organizador = TestBuilder.testUser().validUser().build();

		public BasketBuilder validBasket() {
			testBasket.setTitle("Asado con amigos");
			testBasket.setOwner(organizador);

			return this;
		}

		public Basket build() {
			return testBasket;
		}
	}
	
	public static class CrowdFundingCommonAccountBuilder {

		private CrowdFundingCommonAccount testCrowdFundingCommonAccount = new CrowdFundingCommonAccount();
		private User organizador = TestBuilder.testUser().validUser().build();

		public CrowdFundingCommonAccountBuilder validCrowdFundingCommonAccount() {
			testCrowdFundingCommonAccount.setTitle("Asado con amigos");
			testCrowdFundingCommonAccount.setOwner(organizador);

			return this;
		}

		public CrowdFundingCommonAccount build() {
			return testCrowdFundingCommonAccount;
		}
	}

	public static class CrowdFundingBuilder {

		private CrowdFunding testCrowdFunding = new CrowdFunding();
		private User organizador = TestBuilder.testUser().validUser().build();
		private User user2 = TestBuilder.testUser().validUser().build();
		
		public CrowdFundingBuilder validCrowdFunding() {
			testCrowdFunding.setTitle("Asado con amigos");
			testCrowdFunding.setOwner(organizador);

			List<Item> items = new ArrayList<Item>();
     		items.add(new Item("Asado", 100));
			testCrowdFunding.setItems(items);
			
			List<User> invitados = new ArrayList<User>();
			invitados.add(testCrowdFunding.getOwner());
			user2.setName("Juan");
			invitados.add(user2);
			testCrowdFunding.setGuests(invitados);
			
			return this;
		}

		public CrowdFunding build() {
			return testCrowdFunding;
		}
	}
}
