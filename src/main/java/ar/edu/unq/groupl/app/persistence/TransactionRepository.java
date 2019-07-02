package ar.edu.unq.groupl.app.persistence;

import ar.edu.unq.groupl.app.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query("SELECT user.account.transactions FROM User user where user.email = :email order by date DESC")
	List<Transaction> getAccountTransaction(@Param("email") String email);
	
}
