package ar.edu.unq.groupl.app.persistence;

import ar.edu.unq.groupl.app.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer>{

    @Transactional
    @Query("SELECT loan FROM Loan loan where loan.email = :email")
    List<Loan> getUserLoans(@Param("email") String email);

}
