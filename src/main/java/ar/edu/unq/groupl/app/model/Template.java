package ar.edu.unq.groupl.app.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Template {

	private String title;
	private List<Item> items;
	
}