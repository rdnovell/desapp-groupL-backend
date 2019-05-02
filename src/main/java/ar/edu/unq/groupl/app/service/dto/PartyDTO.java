package ar.edu.unq.groupl.app.service.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class PartyDTO {
	
	private String title;
	private String owner;
	private List<String> guests;
	private List<Integer> items;
	private LocalDate date;
	private LocalDate expirationDate;
	
}