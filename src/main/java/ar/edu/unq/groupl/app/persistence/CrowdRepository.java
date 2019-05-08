package ar.edu.unq.groupl.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.groupl.app.model.CrowdFunding;

@Repository
public interface CrowdRepository extends JpaRepository<CrowdFunding, Integer> {
	
}