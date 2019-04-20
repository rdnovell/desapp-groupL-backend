package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.model.MailJetUser;

public class MailJetUserTest {
	private MailJetUser testMailJetUser;
	@Before
	public void before() {
		testMailJetUser = new MailJetUser("Invitacion","ruben@gmail.com");
	}

	@Test
	public void testGet() {
		assertEquals(testMailJetUser.getName(), "Invitacion");
		assertEquals(testMailJetUser.getMailAddress(), "ruben@gmail.com");
	}
}
