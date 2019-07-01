package ar.edu.unq.groupl.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private TransactionType type;
	private LocalDateTime date;
	private String dateCode;
	private Integer amount;
	private String email;
	private Integer balance;

	public Transaction(TransactionType type, LocalDateTime date, String dateCode, Integer amount, User owner) {
		this.type = type;
		this.date = date;
		this.dateCode = dateCode;
		this.amount = amount;
		this.email = owner.getEmail();
		this.balance = owner.getAccount().getBalance();
		if (type == TransactionType.ADDFUND)this.balance += amount;
		else this.balance -= amount;
	}
}