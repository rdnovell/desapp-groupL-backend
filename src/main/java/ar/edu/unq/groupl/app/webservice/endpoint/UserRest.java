package ar.edu.unq.groupl.app.webservice.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.Party;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.model.util.ListUtil;
import ar.edu.unq.groupl.app.service.UserService;
import ar.edu.unq.groupl.app.service.dto.PartyDTOOnUser;
import ar.edu.unq.groupl.app.service.exception.UnexistException;

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
	@Path("/balance/{email}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response getUserBalance(@PathParam("email") String email) throws UnexistException {
		return ok(userService.getBalance(email));
	}


}