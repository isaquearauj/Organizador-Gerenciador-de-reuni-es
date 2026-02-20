import java.time.LocalDateTime;
import java.util.Scanner;

import sistema.Sistema;
import usuario.Usuario;
import evento.Evento;
import enums.PapelParticipacao;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Sistema sistema = new Sistema();
        Usuario usuarioAtual = null;

        while(true) {

            exibirMenu(usuarioAtual);

            int op = lerInt("\nEscolha: ");

            switch(op) {

                case 1 -> cadastrarUsuario(sistema);

                case 2 -> usuarioAtual = selecionarUsuario(sistema);

                case 3 -> criarEvento(sistema, usuarioAtual);

                case 4 -> adicionarParticipante(sistema, usuarioAtual);

                case 5 -> aceitarConvite(sistema, usuarioAtual);

                case 6 -> recusarConvite(sistema, usuarioAtual);

                case 7 -> sairEvento(sistema, usuarioAtual);

                case 8 -> adicionarHorario(sistema, usuarioAtual);

                case 9 -> informarDisponibilidade(sistema, usuarioAtual);

                case 10 -> confirmarHorario(sistema, usuarioAtual);

                case 11 -> verAgenda(usuarioAtual);

                case 12 -> verNotificacoes(usuarioAtual);

                case 0 -> {
                    System.out.println("\n[INFO] Sistema encerrado.");
                    return;
                }

                default -> System.out.println("\n[ERRO] Opcao invalida.");

            }

        }

    }

    private static void exibirMenu(Usuario usuarioAtual) {

        System.out.println("\n==============================");
        System.out.println(" SISTEMA DE AGENDA ");
        System.out.println("==============================");

        if(usuarioAtual != null)
            System.out.println("Usuario atual: " + usuarioAtual.getNome());
        else
            System.out.println("Nenhum usuario selecionado");

        System.out.println("\nUSUARIOS");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Selecionar usuario");

        System.out.println("\nCRIACAO DE EVENTO");
        System.out.println("3 - Criar evento");
        System.out.println("4 - Adicionar participante (organizador)");

        System.out.println("\nRESPOSTA A CONVITES");
        System.out.println("5 - Aceitar convite");
        System.out.println("6 - Recusar convite");
        System.out.println("7 - Sair do evento");

        System.out.println("\nDEFINICAO DE HORARIOS");
        System.out.println("8 - Adicionar horario (organizador)");
        System.out.println("9 - Informar disponibilidade");
        System.out.println("10 - Confirmar horario (organizador)");

        System.out.println("\nCONSULTAS");
        System.out.println("11 - Ver agenda");
        System.out.println("12 - Ver notificacoes");

        System.out.println("\n0 - Sair");

    }

    private static void cadastrarUsuario(Sistema sistema) {

        System.out.println("\n--- Cadastro de Usuario ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Usuario novo = sistema.cadastrarUsuario(nome, email);

        System.out.println("[SUCESSO] Usuario cadastrado com ID: " + novo.getId());

    }

    private static Usuario selecionarUsuario(Sistema sistema) {

        if(sistema.getUsuarios().isEmpty()) {

            System.out.println("[AVISO] Nenhum usuario cadastrado.");
            return null;

        }

        System.out.println("\n--- Usuarios ---");

        sistema.getUsuarios().forEach(System.out::println);

        long id = lerLong("Digite o ID: ");

        Usuario usuario = sistema.buscarUsuario(id);

        if(usuario == null) {

            System.out.println("[ERRO] Usuario nao encontrado.");
            return null;

        }

        System.out.println("[SUCESSO] Usuario selecionado: " + usuario.getNome());

        return usuario;

    }

    private static void criarEvento(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        System.out.println("\n--- Criar Evento ---");

        System.out.print("Titulo: ");
        String titulo = sc.nextLine();

        System.out.print("Descricao: ");
        String descricao = sc.nextLine();

        System.out.print("Material: ");
        String material = sc.nextLine();

        Evento evento = sistema.criarEvento(
                titulo,
                descricao,
                material,
                usuarioAtual
        );

        usuarioAtual.getAgenda().adicionarEvento(evento);

        System.out.println("[SUCESSO] Evento criado com ID: " + evento.getId());

    }

    private static void adicionarParticipante(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        if(sistema.getEventos().isEmpty()) {

            System.out.println("[AVISO] Nenhum evento cadastrado.");
            return;

        }

        System.out.println("\n--- Eventos ---");
        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento == null) {

            System.out.println("[ERRO] Evento nao encontrado.");
            return;

        }

        System.out.println("\n--- Usuarios ---");
        sistema.getUsuarios().forEach(System.out::println);

        long idUsuario = lerLong("ID participante: ");

        Usuario participante = sistema.buscarUsuario(idUsuario);

        if(participante == null) {

            System.out.println("[ERRO] Usuario nao encontrado.");
            return;

        }

        evento.adicionarParticipante(
                participante,
                PapelParticipacao.PARTICIPANTE
        );

        System.out.println("[SUCESSO] Participante adicionado.");

    }

    private static void aceitarConvite(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento != null)
            evento.aceitarConvite(usuarioAtual);

    }

    private static void recusarConvite(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento != null)
            evento.recusarConvite(usuarioAtual);

    }

    private static void sairEvento(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento != null)
            evento.sairEvento(usuarioAtual);

    }

    private static void adicionarHorario(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento == null) {

            System.out.println("[ERRO] Evento nao encontrado.");
            return;

        }

        LocalDateTime inicio = lerData("Inicio");
        LocalDateTime fim = lerData("Fim");

        evento.adicionarOpcao(inicio, fim);

        System.out.println("[SUCESSO] Horario adicionado.");

    }

    private static void informarDisponibilidade(Sistema sistema,
                                                Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento == null) {

            System.out.println("[ERRO] Evento nao encontrado.");
            return;

        }

        evento.getOpcoes().forEach(System.out::println);

        long idOpcao = lerLong("ID opcao: ");

        System.out.println("\n1 - Disponivel");
        System.out.println("2 - Indisponivel");

        int escolha = lerInt("Escolha: ");

        if(escolha == 1)
            evento.informarDisponibilidade(
                    usuarioAtual,
                    idOpcao
            );

        else if(escolha == 2)
            evento.informarIndisponibilidade(
                    usuarioAtual,
                    idOpcao
            );

        else
            System.out.println("[ERRO] Opcao invalida.");

    }

    private static void confirmarHorario(Sistema sistema, Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        sistema.getEventos().forEach(System.out::println);

        long idEvento = lerLong("ID evento: ");

        Evento evento = sistema.buscarEvento(idEvento);

        if(evento == null) {

            System.out.println("[ERRO] Evento nao encontrado.");
            return;

        }

        evento.getOpcoes().forEach(System.out::println);

        long idOpcao = lerLong("Escolha opcao: ");

        evento.confirmarHorario(idOpcao, usuarioAtual);

    }

    private static void verAgenda(Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        System.out.println("\n1 - Todos");
        System.out.println("2 - Confirmados");
        System.out.println("3 - Pendentes");

        int op = lerInt("Escolha: ");

        switch(op) {

            case 1 -> usuarioAtual.getAgenda().listarTodos();
            case 2 -> usuarioAtual.getAgenda().listarConfirmados();
            case 3 -> usuarioAtual.getAgenda().listarPendentes();

        }

    }

    private static void verNotificacoes(Usuario usuarioAtual) {

        if(!validarUsuario(usuarioAtual)) return;

        usuarioAtual.listarNotificacoes();

    }

    private static boolean validarUsuario(Usuario usuario) {

        if(usuario == null) {

            System.out.println("[AVISO] Selecione um usuario primeiro.");
            return false;

        }

        return true;

    }

    private static int lerInt(String msg) {

        while(true) {

            try {

                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());

            } catch(Exception e) {

                System.out.println("[ERRO] Digite um numero valido.");

            }

        }

    }

    private static long lerLong(String msg) {

        while(true) {

            try {

                System.out.print(msg);
                return Long.parseLong(sc.nextLine());

            } catch(Exception e) {

                System.out.println("[ERRO] Digite um numero valido.");

            }

        }

    }

    private static LocalDateTime lerData(String msg) {

        while(true) {

            try {

                System.out.print(msg + " (YYYY-MM-DDTHH:MM): ");
                return LocalDateTime.parse(sc.nextLine());

            } catch(Exception e) {

                System.out.println("[ERRO] Formato invalido.");
                System.out.println("Exemplo: 2026-03-10T14:00");

            }

        }

    }

}