package ar.edu.unq.groupl.app.webservice.endpoint;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.UserService;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnUser;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/user")
public class UserRest extends Rest {
	
	@Autowired private UserService userService;
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createUser(User user) throws InvalidParameterException {
		userService.createUser(user);
		return ok();
	}
	
	@GET
	@Path("/guest-events")
	@Produces(APPLICATION_JSON)
	public Response getGuestEvents(@QueryParam("email") String email) throws UnexistException {
		List<Event> events = userService.getGuestEvents(email);
		return ok(ListUtil.toList(events.stream().map(event -> {
			if (event instanceof Party) {
				return new PartyDTOOnUser((Party) event);
			}
			return null;
		})));
	}
	
	@GET
	@Path("/assisted-events")
	@Produces(APPLICATION_JSON)
	public Response getAssitedEvents(@QueryParam("email") String email) throws UnexistException {
		List<Event> events = userService.getAssitedEvents(email);
		return ok(ListUtil.toList(events.stream().map(event -> {
			if (event instanceof Party) {
				return new PartyDTOOnUser((Party) event);
			}
			return null;
		})));
	}
	
	@GET
	@Path("/owner-events")
	@Produces(APPLICATION_JSON)
	public Response getOwnertEvents(@QueryParam("email") String email) throws UnexistException {
		List<Event> events = userService.getOwnerEvents(email);
		return ok(ListUtil.toList(events.stream().map(event -> {
			if (event instanceof Party) {
				return new PartyDTOOnUser((Party) event);
			}
			return null;
		})));
	}

	@GET
	@Path("/owner-account-transaction")
	@Produces(APPLICATION_JSON)
	public Response getAccountTransaction(@QueryParam("email") String email) throws UnexistException {
		return ok(userService.getAccountTransaction(email));
	}

	@GET
	@Path("/balance/{email}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response getUserBalance(@PathParam("email") String email) throws UnexistException {
		return ok(userService.getBalance(email));
	}


}