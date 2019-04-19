package service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.mail.SendFailedException;

import org.junit.Test;

import ar.edu.unq.grupol.app.service.EmailSender;

public class EmailSenderTest {

	@Test
	public void testEnvioDeEmail() throws SendFailedException {
		EmailSender emailMock = mock(EmailSender.class);
		emailMock.send(any(), any());
		verify(emailMock, times(1)).send(any(), any());
	}

	@Test
	public void testParaVerificarLaInstancia() {
		EmailSender emailSenderInstance = EmailSender.getInstance();
		assertNotNull(emailSenderInstance);
	}

	@Test(expected = SendFailedException.class)
	public void testParaVerificarDireccionEnvioInvalido() throws SendFailedException {

		EmailSender emailSenderInstance = EmailSender.getInstance();
		emailSenderInstance.send("", "");

	}
}
