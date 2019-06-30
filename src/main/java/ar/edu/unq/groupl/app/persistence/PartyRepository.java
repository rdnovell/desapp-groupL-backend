package ar.edu.unq.groupl.app.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unq.groupl.app.model.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Party party where party.id = :id") 
	void removeById(@Param("id") Integer id);
	

	@Query(value = "SELECT * FROM parties where id in (select event_id from (select count(*),event_id from guests_and_events group by event_id order by 1 desc) as events) limit 1",
	nativeQuery = true)
	List<Party> getTopEvents();
	
}
