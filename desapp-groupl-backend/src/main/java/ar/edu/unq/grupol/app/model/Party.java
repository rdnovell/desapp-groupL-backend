package ar.edu.unq.grupol.app.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Party extends Event {

    public int getPartyCost() {
        return getItems().stream().mapToInt(item -> item.getValue()).sum() * getConfirmedGuests().size();
    }
}
