package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.mail.SendFailedException;

import org.junit.Test;

import ar.edu.unq.grupol.app.model.EmailSender;

public class EmailSenderTest {

	@Test
	public void testEnvioDeEmail() {
		EmailSender emailMock = mock(EmailSender.class);
		emailMock.send(any(), any());
		verify(emailMock, times(1)).send(any(), any());
	}

	@Test
	public void testParaVerificarLaInstancia() {
		EmailSender emailSenderInstance = EmailSender.getInstance();
		assertNotNull(emailSenderInstance);
	}

	@Test
	public void testParaVerificarDireccionEnvioInvalido() {
		try {
	    	EmailSender emailSenderInstance = EmailSender.getInstance();
	    	emailSenderInstance.send("", "");
		} catch (Exception e) {
			System.out.println("gil");
		}
	}
}
