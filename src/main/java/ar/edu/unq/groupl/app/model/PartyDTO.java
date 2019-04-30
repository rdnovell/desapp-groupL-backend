package ar.edu.unq.groupl.app.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class PartyDTO {
	
	private String title;
	private Integer owner;
	private List<Integer> guests;
	private List<Integer> items;
	private LocalDate date;
	private LocalDate expirationDate;
	
}