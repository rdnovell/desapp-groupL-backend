package ar.edu.unq.grupol.app.persistence;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ar.edu.unq.grupol.app.model.User;

@Component
public class UserRepository {
	
	private List<User> users = new ArrayList<User>();
	private int index = 0;
	
	public void save(User user) {
		user.setId(index);
		index++;
		users.add(user);
	}
	
	public User get(Integer id) {
		//TODO: ASUMIMOS QUE EL USUARIO EXISTE.
		return users.get(id);
	}

}
