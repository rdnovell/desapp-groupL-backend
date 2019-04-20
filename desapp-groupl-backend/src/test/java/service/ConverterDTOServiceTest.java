package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import ar.edu.unq.grupol.app.model.Item;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.PartyDTO;
import ar.edu.unq.grupol.app.model.User;
import ar.edu.unq.grupol.app.persistence.ItemRepository;
import ar.edu.unq.grupol.app.persistence.UserRepository;
import ar.edu.unq.grupol.app.service.ConverterDTOService;
import model.TestBuilder;

public class ConverterDTOServiceTest {

	private ConverterDTOService testConverterDTOService;
	private PartyDTO partyDTO;
	
	@Before
	public void before() {
		testConverterDTOService = new ConverterDTOService();
		partyDTO = new PartyDTO();
		ReflectionTestUtils.setField(partyDTO, "title", "Asado con amigos");
		ReflectionTestUtils.setField(partyDTO, "owner", 0);
		ReflectionTestUtils.setField(partyDTO, "guests", Arrays.asList(0));
		ReflectionTestUtils.setField(partyDTO, "items", Arrays.asList(0));
		ReflectionTestUtils.setField(partyDTO, "date",  LocalDate.now().plusDays(10));
		ReflectionTestUtils.setField(partyDTO, "expirationDate", LocalDate.now().plusDays(8));
		
		User user = TestBuilder.testUser().validUser().build();
		Item item = new Item(0, "asado", 100);
		UserRepository userRepositoryMock = mock(UserRepository.class);
		ItemRepository itemRepositoryMock = mock(ItemRepository.class);
		
		when(userRepositoryMock.get(any())).thenReturn(user);
		when(itemRepositoryMock.get(any())).thenReturn(item);
		
		ReflectionTestUtils.setField(testConverterDTOService, "userRepository", userRepositoryMock);
		ReflectionTestUtils.setField(testConverterDTOService, "itemRepository", itemRepositoryMock);
	}
	
	@Test
	public void testConverter() {
		Party party = testConverterDTOService.converter(partyDTO);
		assertEquals(party.getTitle(), partyDTO.getTitle());
		assertEquals(party.getOwner().getId(), partyDTO.getOwner());
		assertEquals(party.getGuests().get(0).getId(), partyDTO.getGuests().get(0));
		assertEquals(party.getItems().get(0).getId(), partyDTO.getItems().get(0));
		assertEquals(party.getDate(), partyDTO.getDate());
		assertEquals(party.getExpirationDate(), partyDTO.getExpirationDate());
	}
}
