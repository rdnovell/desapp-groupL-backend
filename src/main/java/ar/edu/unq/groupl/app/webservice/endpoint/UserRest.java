package ar.edu.unq.groupl.app.webservice.endpoint;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.model.exception.InvalidParameterException;
import ar.edu.unq.groupl.app.service.UserService;
import ar.edu.unq.groupl.app.service.dto.LoginDTO;
import ar.edu.unq.groupl.app.service.exception.LoginException;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import ar.edu.unq.groupl.app.webservice.util.RestUtils;

@Component
@Path("/user")
public class UserRest extends Rest {
	
	@Autowired private UserService userService;
	
	@POST
	@Path("/create")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createUser(User user) throws InvalidParameterException {
		userService.createUser(user);
		return ok();
	}
	
	@GET
	@Path("/login")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response getUser(LoginDTO loginDTO) throws UnexistException {
		try {
			return ok(userService.login(loginDTO.getUsername(), loginDTO.getPassword()));
		} catch (LoginException loginException) {
			return RestUtils.unauthorized(loginException.getMessage());
		}
	}
	
	@GET
	@Path("/balance/{email}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response getUserBalance(@PathParam("email") String email) throws UnexistException {
		return ok(userService.getBalance(email));
	}

}