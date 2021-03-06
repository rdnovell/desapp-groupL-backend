package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.Template;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;

public class TemplateTest {
	private Party testFiesta;
	private Template testTemplate;
	private List<Item> testItems = new ArrayList<Item>();
	
	@Before
	public void before() {
		testFiesta = TestBuilder.testParty().validFiesta().build();
		testItems.add(new Item("papa", 10));
		testItems.add(new Item("aceite", 40));
		testTemplate = new Template("Template papas fritas", testItems);
		
	}

	@Test
	public void testTitleOfTemplate() throws InvalidParameterException {
		assertEquals(testTemplate.getTitle(), "Template papas fritas");
	}
	
	@Test
	public void testOnAddTemplateMustIncrementItemsSize() throws InvalidParameterException {
		assertEquals(testFiesta.getItems().size(), 1);
		testFiesta.withTemplate(testTemplate);
		assertEquals(testFiesta.getItems().size(), 3);
	}

}
