package usuario;

import java.util.ArrayList;
import java.util.List;
import notificacao.Notificacao;

public class Usuario {

    private static long contador = 1;

    private long id;
    private String nome;
    private String email;

    private Agenda agenda;

    private List<Notificacao> notificacoes;

    public Usuario(String nome, String email) {

        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.agenda = new Agenda(this);
        this.notificacoes = new ArrayList<>();

    }

    public long getId() { return id; }

    public String getNome() { return nome; }

    public Agenda getAgenda() { return agenda; }

    public void adicionarNotificacao(Notificacao n) {

        notificacoes.add(n);

    }

    public void listarNotificacoes() {

        for(Notificacao n : notificacoes)
            System.out.println(n);

    }

    @Override
    public String toString() {

        return id + " - " + nome;

    }

}
