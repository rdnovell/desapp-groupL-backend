package ar.edu.unq.groupl.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.unq.groupl.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
}