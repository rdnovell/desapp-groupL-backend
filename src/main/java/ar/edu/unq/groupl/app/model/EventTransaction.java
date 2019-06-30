package ar.edu.unq.groupl.app.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_transactions")
@NoArgsConstructor
@Getter @Setter
public class EventTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="email_confirmated")
	private User user;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	private Event event;
	
	private String instant;
	
	public EventTransaction(User user, Event event) {
		this.user = user;
		this.event = event;
		this.instant = Instant.now().toString();
	}

}
