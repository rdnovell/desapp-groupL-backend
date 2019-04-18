package ar.edu.unq.grupol.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
	private Integer id;
	private String title;
	private int value;
}
