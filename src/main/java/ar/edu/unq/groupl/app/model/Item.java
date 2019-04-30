package ar.edu.unq.groupl.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Item {
	
	@Setter private Integer id;
	private String title;
	private int value;
	
}