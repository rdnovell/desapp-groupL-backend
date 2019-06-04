package ar.edu.unq.groupl.app.webservice.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Item;
import ar.edu.unq.groupl.app.service.ItemService;

@Component
@Path("/items")
public class ItemRest extends Rest {
	
	@Autowired private ItemService itemService;
	
	@GET
	@Produces(APPLICATION_JSON)
	public Response getItems() {
		return ok(itemService.getItems());
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createItem(Item item) {
		itemService.createItem(item);
		return ok();
	}

}
