package ar.edu.unq.groupl.app.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unq.groupl.app.model.EventTransaction;

@Repository
public interface EventTransactionRepository extends JpaRepository<EventTransaction, Integer> {
	
	@Transactional
	@Modifying
	@Query("DELETE FROM EventTransaction eventTransaction where eventTransaction.event.id = :id") 
	void removeById(@Param("id") Integer id);
	
	@Query("FROM EventTransaction eventTransaction where eventTransaction.event.id = :id") 
	List<EventTransaction> getTransactionsFromEvent(@Param("id") Integer id);
	
}
