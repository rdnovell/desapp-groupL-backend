package ar.edu.unq.groupl.app.webservice.endpoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.service.EventService;
import ar.edu.unq.groupl.app.service.dto.ConverterDTOService;
import ar.edu.unq.groupl.app.service.dto.PartyDTO;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;

@Component
@Path("/event")
public class EventRest extends Rest {
	
	@Autowired private EventService eventService;
	@Autowired private ConverterDTOService converterDTOService;
	
	@POST
	@Path("/createParty")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createParty(PartyDTO partyDTO) throws InvalidParameterException {
		Party party = converterDTOService.converter(partyDTO);
		eventService.createParty(party);
		return ok();
	}

}