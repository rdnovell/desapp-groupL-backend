package ar.edu.unq.grupol.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {

	private TransactionType type;
	private LocalDateTime date;
	private Integer amount;
	
}
