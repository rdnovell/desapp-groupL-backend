package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class PartyDTOOnCreate {

	private String title;
	private String owner;
	private List<String> items;
	private List<String> guests;
	private LocalDate date;
	private LocalDate expirationDate;

}
