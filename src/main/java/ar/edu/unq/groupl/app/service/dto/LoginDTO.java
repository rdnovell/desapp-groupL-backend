package ar.edu.unq.groupl.app.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {

	@NotNull private String username;
	@NotNull private String password;
	
}
