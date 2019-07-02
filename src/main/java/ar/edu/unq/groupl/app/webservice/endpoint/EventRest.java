package ar.edu.unq.groupl.app.webservice.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collections;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.EventTransaction;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.EventService;
import ar.edu.unq.groupl.app.service.dto.BasketDTO;
import ar.edu.unq.groupl.app.service.dto.ConverterDTOService;
import ar.edu.unq.groupl.app.service.dto.CrowdDTO;
import ar.edu.unq.groupl.app.service.dto.PartyDTO;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnCreate;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnUser;
import ar.edu.unq.groupl.app.service.dto.EventItemsDTO;
import ar.edu.unq.groupl.app.service.dto.EventTransactionDTO;
import ar.edu.unq.groupl.app.service.dto.EventUsersDTO;
import ar.edu.unq.groupl.app.service.dto.UserConfirmatedDTO;
import ar.edu.unq.groupl.app.service.exception.UnexistException;

@Component
@Path("/event")
public class EventRest extends Rest {
	
	@Autowired private EventService eventService;
	@Autowired private ConverterDTOService converterDTOService;
	
	@POST
	@Path("/party")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createParty(PartyDTOOnCreate partyDTO) throws InvalidParameterException {
		eventService.createParty(partyDTO);
		return ok();
	}
	
	@DELETE
	@Path("/party/{id}")
	@Produces(APPLICATION_JSON)
	public Response removeParty(@PathParam("id") Integer partyId) {
		eventService.removeParty(partyId);
		return ok();
	}
	
	@GET
	@Path("/party/transactions")
	@Produces(APPLICATION_JSON)
	public Response getTransactionsTimeByZone(@QueryParam("zone") String zone, @QueryParam("owner") String owner) {
		List<EventTransactionDTO> transactions = eventService.getTransactionsTimeByZone(zone, owner);
		Collections.reverse(transactions);
		return ok(transactions);
	}
	
	@PUT
	@Path("/guest-confirmated")
	@Produces(APPLICATION_JSON)
	public Response confirmAssistance(UserConfirmatedDTO userConfirmatedDTO) throws EventException {
		eventService.confirmAssistance(userConfirmatedDTO);
		return ok();
	}
	
	@PUT
	@Path("/party/items")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response updateItemsFromParty(EventItemsDTO partyItem) {
		eventService.updateItemsFromParty(partyItem.getEventId(), partyItem.getItemsTitle());
		return ok();
	}
	
	@PUT
	@Path("/party/guests")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response updateGuestsFromParty(EventUsersDTO partyUsers) {
		eventService.updateGuestsFromParty(partyUsers.getEventId(), partyUsers.getUserEmails());
		return ok();
	}
	
	@POST
	@Path("/createBasket")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createParty(BasketDTO basketDTO) throws InvalidParameterException {
		Basket basket = converterDTOService.converter(basketDTO);
		eventService.createBasket(basket);
		return ok();
	}
	
	@POST
	@Path("/createCrowd")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createParty(CrowdDTO crowdDTO) throws InvalidParameterException {
		CrowdFunding crowd = converterDTOService.converter(crowdDTO);
		eventService.createCrowdFunding(crowd);
		return ok();
	}
	
	@GET
	@Path("/top-events")
	@Produces(APPLICATION_JSON)
	public Response getTopEvents() throws UnexistException {
		List<Event> events = eventService.getTopEvents();
		return ok(ListUtil.toList(events.stream().map(event -> {
			if (event instanceof Party) {
				return new PartyDTOOnUser((Party) event);
			}
			return null;
		})));
	}
}