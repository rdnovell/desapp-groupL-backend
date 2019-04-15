package ar.edu.unq.grupol.app.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Fiesta extends Evento {

    public int getCosto() {
        return getItems().stream().mapToInt(item -> item.getCosto()).sum() * getInvitadosConfirmados().size();
    }
}
