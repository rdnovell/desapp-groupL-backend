package ar.edu.unq.groupl.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import ar.edu.unq.groupl.app.model.exception.InvalidAmount;
import lombok.Getter;
import lombok.Setter;

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
