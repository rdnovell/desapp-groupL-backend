package ar.edu.unq.groupl.app.persistence;

import ar.edu.unq.groupl.app.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>{

}
