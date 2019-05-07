package ar.edu.unq.groupl.app.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import ar.edu.unq.groupl.app.model.exception.EventException;
import ar.edu.unq.groupl.app.model.exception.InvitationExpiredException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Party extends Event {
	
	@NotNull(message = "Expiration date must be defined.")
	@Setter
	@Getter
	private LocalDate expirationDate;
	
	@Override
	public void addConfirmedGuests(User user) throws EventException {
		if (LocalDate.now().isBefore(expirationDate)) {
			super.addConfirmedGuests(user);
		} else {
			throw new InvitationExpiredException("The invitation has expired.");
		}
	}

    public int getPartyCost() {
        return getTotalCost() * getConfirmedGuests().size();
    }
    
}