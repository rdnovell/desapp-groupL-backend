package ar.edu.unq.groupl.app.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unq.groupl.app.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	
	@Query("SELECT a.balance FROM Account a where a.email = :email") 
	Optional<Integer> getBalance(@Param("email") String email);
}
