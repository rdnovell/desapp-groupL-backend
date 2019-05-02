package ar.edu.unq.groupl.app.webservice.util;

import java.util.function.Function;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RestUtils {
	
	public static Response ok(Function<JsonObjectBuilder, JsonObjectBuilder> addValues) {
		return Response.ok(addValues.apply(Json.createObjectBuilder()).build().toString()).build();
	}
	
	public static <T> Response ok(T object) {
		return Response.ok(object).build();
	}
	
	public static Response ok(){
		return Response.ok().build();
	}

	private static Response getResponse(Status status, String message) {
		return Response.status(status).entity(
						Json.createObjectBuilder().add("message", message).build().toString()
					).build();
	}
	
	public static Response badRequest(String message) {
		return getResponse(Status.BAD_REQUEST, message);
	}
	
	public static Response notFound(String message) {
		return getResponse(Status.NOT_FOUND, message);
	}
	
	public static Response unauthorized(String message) {
		return getResponse(Status.UNAUTHORIZED, message);
	}
	
	public static Response internalServerError(String mensaje) {
		return getResponse(Status.INTERNAL_SERVER_ERROR, mensaje);
	}
	
}