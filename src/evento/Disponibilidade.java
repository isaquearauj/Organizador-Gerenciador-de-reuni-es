package evento;

import usuario.Usuario;
import enums.StatusDisponibilidade;
import java.time.LocalDateTime;

public class Disponibilidade {

    private Usuario usuario;
    private OpcaoHorario opcaoHorario;
    private StatusDisponibilidade status;
    private LocalDateTime dataResposta;

    public Disponibilidade(Usuario usuario,
                           OpcaoHorario opcaoHorario,
                           StatusDisponibilidade status) {

        this.usuario = usuario;
        this.opcaoHorario = opcaoHorario;
        this.status = status;
        this.dataResposta = LocalDateTime.now();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public StatusDisponibilidade getStatus() {
        return status;
    }

}