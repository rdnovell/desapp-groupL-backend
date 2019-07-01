package ar.edu.unq.groupl.app.model;

import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter @Setter
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	private String email;

	@JsonIgnore
	@OneToOne
    @MapsId
    private User user;

	private Integer balance;

	@OneToMany(
			mappedBy = "email",
			cascade = CascadeType.ALL
	)
	private List<Transaction> transactions;

	public Account() {
		balance = 0;
	}

	public Account(User user) {
		balance = 0;
		this.user = user;
	}

	public void addMoney(Integer amount) {
		balance += amount;
	}

	public void getMoney(Integer amount) throws InvalidAmount {
		if (balance >= amount) {
			balance -= amount;
		} else {
			throw new InvalidAmount("Transaction cannot be done, insufficient balance.");
		}
	}

}
