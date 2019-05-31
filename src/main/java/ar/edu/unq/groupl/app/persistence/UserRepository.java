package ar.edu.unq.groupl.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ar.edu.unq.groupl.app.model.Event;
import ar.edu.unq.groupl.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT user.guestedEvents FROM User user where user.email = :email") 
	List<Event> getGuestEvents(@Param("email") String email);
	
	@Query("SELECT user.eventsOwner FROM User user where user.email = :email") 
	List<Event> getOwnerEvents(@Param("email") String email);
	
}