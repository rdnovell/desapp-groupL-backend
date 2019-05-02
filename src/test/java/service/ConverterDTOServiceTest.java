package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.persistence.ItemRepository;
import ar.edu.unq.groupl.app.persistence.UserRepository;
import ar.edu.unq.groupl.app.service.dto.ConverterDTOService;
import ar.edu.unq.groupl.app.service.dto.PartyDTO;
import model.TestBuilder;

public class ConverterDTOServiceTest {

	private ConverterDTOService testConverterDTOService;
	private PartyDTO partyDTO;
	
	@Before
	public void before() {
		testConverterDTOService = new ConverterDTOService();
		partyDTO = new PartyDTO();
		ReflectionTestUtils.setField(partyDTO, "title", "Asado con amigos");
		ReflectionTestUtils.setField(partyDTO, "owner", "test@gmail.com");
		ReflectionTestUtils.setField(partyDTO, "guests", Arrays.asList("testguest@gmail.com"));
		ReflectionTestUtils.setField(partyDTO, "items", Arrays.asList(0));
		ReflectionTestUtils.setField(partyDTO, "date",  LocalDate.now().plusDays(10));
		ReflectionTestUtils.setField(partyDTO, "expirationDate", LocalDate.now().plusDays(8));
		Item item = new Item(1, "asado", 100);
		item.setId(0);
		UserRepository userRepositoryMock = mock(UserRepository.class);
		ItemRepository itemRepositoryMock = mock(ItemRepository.class);
		User owner = mock(User.class);
		when(owner.getEmail()).thenReturn("test@gmail.com");
		User guest = mock(User.class);
		when(guest.getEmail()).thenReturn("testguest@gmail.com");
		
		when(userRepositoryMock.findById("test@gmail.com")).thenReturn(Optional.of(owner));
		when(userRepositoryMock.findById("testguest@gmail.com")).thenReturn(Optional.of(guest));
		when(itemRepositoryMock.get(any())).thenReturn(item);
		
		ReflectionTestUtils.setField(testConverterDTOService, "userRepository", userRepositoryMock);
		ReflectionTestUtils.setField(testConverterDTOService, "itemRepository", itemRepositoryMock);
	}
	
	@Test
	public void testConverter() {
		Party party = testConverterDTOService.converter(partyDTO);
		assertEquals(party.getTitle(), partyDTO.getTitle());
		assertEquals(party.getOwner().getEmail(), partyDTO.getOwner());
		assertEquals(party.getGuests().get(0).getEmail(), partyDTO.getGuests().get(0));
		assertEquals(party.getItems().get(0).getId(), partyDTO.getItems().get(0));
		assertEquals(party.getDate(), partyDTO.getDate());
		assertEquals(party.getExpirationDate(), partyDTO.getExpirationDate());
	}
}
