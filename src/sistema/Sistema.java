package sistema;

import java.util.ArrayList;
import java.util.List;
import usuario.Usuario;
import evento.Evento;

public class Sistema {

    private List<Usuario> usuarios;
    private List<Evento> eventos;

    public Sistema() {

        usuarios = new ArrayList<>();
        eventos = new ArrayList<>();

    }

    public Usuario cadastrarUsuario(String nome,
                                    String email) {

        Usuario u = new Usuario(nome, email);

        usuarios.add(u);

        return u;

    }

    public List<Usuario> getUsuarios() {

        return usuarios;

    }

    public Usuario buscarUsuario(long id) {

        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

    }

    public Evento criarEvento(String titulo,
                              String desc,
                              String mat,
                              Usuario org) {

        Evento e = new Evento(titulo, desc, mat, org);

        eventos.add(e);

        return e;

    }

    public Evento buscarEvento(long id) {

        return eventos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

    }

    public List<Evento> getEventos() {

        return eventos;

    }

}
