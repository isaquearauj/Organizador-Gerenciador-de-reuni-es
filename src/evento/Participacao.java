package evento;

import usuario.Usuario;
import enums.PapelParticipacao;
import enums.StatusConvite;

public class Participacao {

    private static long contador = 1;

    private long id;
    private Usuario usuario;
    private Evento evento;

    private PapelParticipacao papel;
    private StatusConvite statusConvite;

    public Participacao(Usuario usuario,
                        Evento evento,
                        PapelParticipacao papel) {

        this.id = contador++;
        this.usuario = usuario;
        this.evento = evento;
        this.papel = papel;

        if(papel == PapelParticipacao.ORGANIZADOR)
            this.statusConvite = StatusConvite.ACEITO;
        else
            this.statusConvite = StatusConvite.PENDENTE;

    }

    public void aceitar() {

        this.statusConvite = StatusConvite.ACEITO;

    }

    public void recusar() {

        this.statusConvite = StatusConvite.RECUSADO;

    }

    public Evento getEvento() {

        return evento;

    }

    public Usuario getUsuario() {

        return usuario;

    }

    public StatusConvite getStatusConvite() {
        return statusConvite;
    }

}
