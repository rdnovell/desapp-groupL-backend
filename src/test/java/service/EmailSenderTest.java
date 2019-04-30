package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.Test;

import ar.edu.unq.groupl.app.service.EmailSender;

public class EmailSenderTest {

	@Test
	public void testSendMail() {
		EmailSender emailMock = mock(EmailSender.class);
		emailMock.send(any());
		verify(emailMock, times(1)).send(any());
	}
	
}
