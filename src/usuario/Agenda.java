package usuario;

import java.util.ArrayList;
import java.util.List;
import evento.Evento;
import enums.StatusEvento;

public class Agenda {

    private Usuario dono;
    private List<Evento> eventos;

    public Agenda(Usuario dono) {

        this.dono = dono;
        this.eventos = new ArrayList<>();

    }

    public void adicionarEvento(Evento evento) {

        if(!eventos.contains(evento))
            eventos.add(evento);

    }

    public void listarTodos() {

        if(eventos.isEmpty()) {

            System.out.println("[AVISO] Nenhum evento na agenda.");
            return;

        }

        System.out.println("\n--- TODOS EVENTOS ---");

        eventos.forEach(System.out::println);

    }

    public void listarConfirmados() {

        System.out.println("\n--- EVENTOS CONFIRMADOS ---");

        eventos.stream()
                .filter(e -> e.getStatus() == StatusEvento.CONFIRMADO)
                .forEach(System.out::println);

    }

    public void listarPendentes() {

        System.out.println("\n--- EVENTOS PENDENTES ---");

        eventos.stream()
                .filter(e -> e.getStatus() == StatusEvento.PENDENTE)
                .forEach(System.out::println);

    }

}