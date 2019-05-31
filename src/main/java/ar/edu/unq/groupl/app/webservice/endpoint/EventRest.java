package ar.edu.unq.groupl.app.webservice.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Basket;
import ar.edu.unq.groupl.app.model.CrowdFunding;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.service.EventService;
import ar.edu.unq.groupl.app.service.dto.BasketDTO;
import ar.edu.unq.groupl.app.service.dto.ConverterDTOService;
import ar.edu.unq.groupl.app.service.dto.CrowdDTO;
import ar.edu.unq.groupl.app.service.dto.PartyDTO;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnCreate;

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
//		Party party = converterDTOService.converter(partyDTO);
//		eventService.createParty(party);
		eventService.createParty(partyDTO);
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
}