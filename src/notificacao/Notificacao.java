package notificacao;

import usuario.Usuario;
import enums.TipoNotificacao;
import java.time.LocalDateTime;

public class Notificacao {

    private static long contador = 1;

    private long id;

    private Usuario destinatario;

    private String mensagem;

    private TipoNotificacao tipo;

    private LocalDateTime data;

    public Notificacao(Usuario destinatario,
                       String mensagem,
                       TipoNotificacao tipo) {

        this.id = contador++;

        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.data = LocalDateTime.now();

    }

    @Override
    public String toString() {

        return "[" + tipo + "] " + mensagem +
                " (" + data + ")";

    }

}
