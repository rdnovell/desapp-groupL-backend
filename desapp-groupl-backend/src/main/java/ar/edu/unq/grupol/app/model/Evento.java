package ar.edu.unq.grupol.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Evento {
	private String titulo;
	private User organizador;
	private List<User> invitados;
	@Setter(AccessLevel.NONE)
	private List<User> invitadosConfirmados = new ArrayList<User>();
	private List<Item> items;

	public void agregarInvitadoConfirmado(User user){
		invitadosConfirmados.add(user);
	}

    public void enviarInvitaciones(){
        invitados.stream().forEach(invitado -> EmailSender.send(titulo,invitado.getEmail()));
    }
}
