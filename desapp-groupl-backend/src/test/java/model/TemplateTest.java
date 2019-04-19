package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.grupol.app.exceptions.InvalidParameterException;
import ar.edu.unq.grupol.app.model.Item;
import ar.edu.unq.grupol.app.model.Party;
import ar.edu.unq.grupol.app.model.Template;

public class TemplateTest {
	private Party testFiesta;
	private Template testTemplate;
	private List<Item> testItems = new ArrayList<Item>();
	
	@Before
	public void before() {
		testFiesta = TestBuilder.testParty().validFiesta().build();
		testItems.add(new Item(1, "papa", 10));
		testItems.add(new Item(2, "aceite", 40));
		testTemplate = new Template("Template papas fritas", testItems);
		
	}

	@Test
	public void testNombreDeUnTemplate() throws InvalidParameterException {
		assertEquals(testTemplate.getTitle(), "Template papas fritas");
	}
	
	@Test
	public void testAlAgregarUnTemplateSeModificaLaListaDeItems() throws InvalidParameterException {
		assertEquals(testFiesta.getItems().size(), 1);
		testFiesta.withTemplate(testTemplate);
		assertEquals(testFiesta.getItems().size(), 3);
	}

}
