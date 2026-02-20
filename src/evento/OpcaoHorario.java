package evento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.StatusDisponibilidade;
import usuario.Usuario;

public class OpcaoHorario {

    private static long contador = 1;

    private long id;

    private Evento evento;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private List<Disponibilidade> disponibilidades;

    public OpcaoHorario(Evento evento,
                        LocalDateTime inicio,
                        LocalDateTime fim) {

        this.id = contador++;
        this.evento = evento;
        this.inicio = inicio;
        this.fim = fim;
        this.disponibilidades = new ArrayList<>();

    }

    public long getId() {
        return id;
    }

    public void adicionarDisponibilidade(Disponibilidade d) {

        for(Disponibilidade disp : disponibilidades) {

            if(disp.getUsuario().equals(d.getUsuario())) {

                System.out.println("[AVISO] Disponibilidade ja registrada.");
                return;

            }

        }

        disponibilidades.add(d);

    }

    public void removerDisponibilidade(Usuario usuario) {

        disponibilidades.removeIf(
                disp -> disp.getUsuario().equals(usuario)
        );

    }

    public long contarDisponiveis() {

        return disponibilidades.stream()
                .filter(d ->
                        d.getStatus() ==
                                StatusDisponibilidade.DISPONIVEL
                )
                .count();

    }

    public List<Disponibilidade> getDisponibilidades() {

        return disponibilidades;

    }

    @Override
    public String toString() {

        return id +
                " - " +
                inicio +
                " até " +
                fim +
                " | Disponíveis: " +
                contarDisponiveis();

    }

}