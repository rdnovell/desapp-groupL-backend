package ar.edu.unq.groupl.app.webservice.endpoint;

import ar.edu.unq.groupl.app.model.Loan;
import ar.edu.unq.groupl.app.model.User;
import ar.edu.unq.groupl.app.service.MoneyLoanService;
import ar.edu.unq.groupl.app.service.UserService;
import ar.edu.unq.groupl.app.service.exception.UnexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/loan")
public class LoanRest extends Rest {
	
	@Autowired private UserService userService;
	@Autowired private MoneyLoanService moneyLoanService;

	@GET
	@Path("/loans")
	@Produces(APPLICATION_JSON)
	public Response getUserLoans(@QueryParam("email") String email) throws UnexistException {
		List<Loan> loans = moneyLoanService.getAllUserLoans(email);
		return ok(loans);
	}

	@GET
	@Path("/valid")
	@Produces(APPLICATION_JSON)
	public Response loanAvailable(@QueryParam("email") String email) throws UnexistException {
		return ok(moneyLoanService.loanAvailable(email));
	}

	@POST
	@Path("/create")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response createLoan(@QueryParam("email") String email) throws UnexistException {
		moneyLoanService.createLoan(email);
		return ok();
	}

}