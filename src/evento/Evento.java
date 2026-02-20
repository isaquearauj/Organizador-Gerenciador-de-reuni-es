package evento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.*;
import usuario.Usuario;
import notificacao.Notificacao;

public class Evento {

    private static long contador = 1;

    private long id;

    private String titulo;
    private String descricao;
    private String material;

    private Usuario organizador;

    private LocalDateTime dataCriacao;

    private StatusEvento status;

    private List<OpcaoHorario> opcoes;
    private List<Participacao> participacoes;

    private OpcaoHorario confirmado;

    public Evento(String titulo,
                  String descricao,
                  String material,
                  Usuario organizador) {

        this.id = contador++;

        this.titulo = titulo;
        this.descricao = descricao;
        this.material = material;

        this.organizador = organizador;

        this.dataCriacao = LocalDateTime.now();

        this.status = StatusEvento.PENDENTE;

        this.opcoes = new ArrayList<>();
        this.participacoes = new ArrayList<>();

        adicionarParticipante(organizador, PapelParticipacao.ORGANIZADOR);

    }

    public void adicionarOpcao(LocalDateTime inicio,
                               LocalDateTime fim) {

        opcoes.add(new OpcaoHorario(this, inicio, fim));

    }

    public void adicionarParticipante(Usuario usuario,
                                      PapelParticipacao papel) {

        participacoes.add(new Participacao(usuario, this, papel));

        usuario.getAgenda().adicionarEvento(this);

        usuario.adicionarNotificacao(
                new Notificacao(
                        usuario,
                        "Você foi adicionado ao evento: " + titulo,
                        TipoNotificacao.EVENTO_CRIADO
                )
        );

    }

    public void confirmarHorario(long idOpcao, Usuario usuarioAtual) {

        if(usuarioAtual == null) {

            System.out.println("[ERRO] Usuario invalido.");
            return;

        }

        if(!usuarioAtual.equals(organizador)) {

            System.out.println("[ERRO] Apenas o organizador pode confirmar o evento.");
            return;

        }

        for(OpcaoHorario op : opcoes) {

            if(op.getId() == idOpcao) {

                confirmado = op;
                status = StatusEvento.CONFIRMADO;

                notificarTodos("Evento confirmado: " + titulo);

                return;

            }

        }

        System.out.println("[ERRO] Opcao nao encontrada.");

    }

    private void notificarTodos(String msg) {

        for(Participacao p : participacoes) {

            p.getUsuario().adicionarNotificacao(
                    new Notificacao(
                            p.getUsuario(),
                            msg,
                            TipoNotificacao.EVENTO_CONFIRMADO
                    )
            );

        }

    }

    public void informarDisponibilidade(Usuario usuario,
                                        long idOpcao) {

        registrarDisponibilidade(
                usuario,
                idOpcao,
                StatusDisponibilidade.DISPONIVEL
        );

    }

    public void informarIndisponibilidade(Usuario usuario,
                                          long idOpcao) {

        registrarDisponibilidade(
                usuario,
                idOpcao,
                StatusDisponibilidade.INDISPONIVEL
        );

    }

    private void registrarDisponibilidade(Usuario usuario,
                                          long idOpcao,
                                          StatusDisponibilidade status) {

        Participacao participacao = null;

        for(Participacao p : participacoes) {

            if(p.getUsuario().equals(usuario)) {

                participacao = p;
                break;

            }

        }

        if(participacao == null) {

            System.out.println(
                    "[ERRO] Usuario nao convidado para este evento."
            );
            return;

        }

        if(participacao.getStatusConvite() != StatusConvite.ACEITO) {

            System.out.println(
                    "[ERRO] Aceite o convite antes de informar disponibilidade."
            );
            return;

        }

        for(OpcaoHorario op : opcoes) {

            if(op.getId() == idOpcao) {

                Disponibilidade d =
                        new Disponibilidade(
                                usuario,
                                op,
                                status
                        );

                op.adicionarDisponibilidade(d);

                if(status == StatusDisponibilidade.DISPONIVEL)
                    System.out.println("[SUCESSO] Marcado como DISPONIVEL.");
                else
                    System.out.println("[SUCESSO] Marcado como INDISPONIVEL.");

                return;

            }

        }

        System.out.println("[ERRO] Opcao nao encontrada.");

    }

    public String listarParticipacoesDetalhado() {

        StringBuilder sb = new StringBuilder();

        sb.append("\nOrganizador:");
        sb.append("\n  - ").append(organizador.getNome());

        sb.append("\n\nParticipantes confirmados:");

        boolean encontrouConfirmado = false;

        for(Participacao p : participacoes) {

            if(p.getStatusConvite() == StatusConvite.ACEITO) {

                sb.append("\n  - ")
                        .append(p.getUsuario().getNome());

                encontrouConfirmado = true;

            }

        }

        if(!encontrouConfirmado)
            sb.append("\n  Nenhum.");

        sb.append("\n\nConvidados pendentes:");

        boolean encontrouPendente = false;

        for(Participacao p : participacoes) {

            if(p.getStatusConvite() == StatusConvite.PENDENTE) {

                sb.append("\n  - ")
                        .append(p.getUsuario().getNome());

                encontrouPendente = true;

            }

        }

        if(!encontrouPendente)
            sb.append("\n  Nenhum.");

        return sb.toString();

    }

    public void aceitarConvite(Usuario usuario) {

        for(Participacao p : participacoes) {

            if(p.getUsuario().equals(usuario)) {

                if(p.getStatusConvite() == StatusConvite.ACEITO) {

                    System.out.println("[AVISO] Convite ja aceito.");
                    return;

                }

                if(p.getStatusConvite() == StatusConvite.RECUSADO) {

                    System.out.println("[ERRO] Convite foi recusado anteriormente.");
                    return;

                }

                p.aceitar();

                System.out.println("[SUCESSO] Convite aceito.");
                return;

            }

        }

        System.out.println("[ERRO] Convite nao encontrado.");

    }

    public void recusarConvite(Usuario usuario) {

        for(Participacao p : participacoes) {

            if(p.getUsuario().equals(usuario)) {

                p.recusar();

                System.out.println("[SUCESSO] Convite recusado.");
                return;

            }

        }

        System.out.println("[ERRO] Convite nao encontrado.");

    }

    private void removerDisponibilidades(Usuario usuario) {

        for(OpcaoHorario op : opcoes) {

            op.removerDisponibilidade(usuario);

        }

    }

    public void sairEvento(Usuario usuario) {

        if(usuario.equals(organizador)) {

            System.out.println("[ERRO] O organizador nao pode sair do evento.");
            return;

        }

        Participacao participacao = null;

        for(Participacao p : participacoes) {

            if(p.getUsuario().equals(usuario)) {

                participacao = p;
                break;

            }

        }

        if(participacao == null) {

            System.out.println("[ERRO] Usuario nao participa deste evento.");
            return;

        }

        if(participacao.getStatusConvite() == StatusConvite.RECUSADO) {

            System.out.println("[AVISO] Voce ja recusou este evento.");
            return;

        }

        participacoes.remove(participacao);

        removerDisponibilidades(usuario);

        System.out.println("[SUCESSO] Voce saiu do evento.");

    }

    public StatusEvento getStatus() {

        return status;

    }

    public long getId() {
        return id;
    }

    public Usuario getOrganizador() {

        return organizador;

    }

    public List<OpcaoHorario> getOpcoes() {

        return opcoes;

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n==============================");
        sb.append("\nEvento ID: ").append(id);
        sb.append("\nTitulo: ").append(titulo);
        sb.append("\nDescricao: ").append(descricao);
        sb.append("\nMaterial: ").append(material);
        sb.append("\nStatus: ").append(status);

        sb.append(listarParticipacoesDetalhado());

        if(confirmado != null) {

            sb.append("\n\nHorario confirmado:");
            sb.append("\n  ").append(confirmado.toString());

        }

        sb.append("\n==============================");

        return sb.toString();

    }

}