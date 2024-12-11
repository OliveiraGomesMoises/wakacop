package academy.wakanda.wakacop.sessaovotacao.domain;

import academy.wakanda.wakacop.associado.application.service.AssociadoService;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.sessaovotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaovotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaovotacao.application.api.VotoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;
    private UUID idPauta;
    private Integer tempoDuracao;
    @Enumerated(EnumType.STRING)
    private StatusSessaoVotacao status;
    private LocalDateTime momentoAbertura;
    private LocalDateTime momentoEncerramento;

    @OneToMany(mappedBy = "sessaoVotacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "cpfAssociado")
    private Map<String, VotoPauta> votos;

    public SessaoVotacao(SessaoAberturaRequest sessaoAberturaRequest, Pauta pauta) {
        this.idPauta = pauta.getId();
        this.tempoDuracao = sessaoAberturaRequest.getTempoduracao().orElse(5);
        this.momentoAbertura = LocalDateTime.now();
        this.momentoEncerramento = momentoAbertura.plusMinutes(this.getTempoDuracao());
        this.status = StatusSessaoVotacao.ABERTA;
        this.votos = new HashMap<>();
    }

    public ResultadoSessaoResponse obtemResultado() {
        atualizStatus();
        return new ResultadoSessaoResponse(this);
    }


    public VotoPauta recebeVoto(VotoRequest votoRequest, AssociadoService associadoService) {
        validaSessaoAberta();
        validaAssociado(votoRequest.getCpfAssociado(),associadoService);
        VotoPauta voto = new VotoPauta(this, votoRequest);
        votos.put(votoRequest.getCpfAssociado(), voto);
        return voto;
    }

    private void validaSessaoAberta() {
        atualizStatus();
        if (this.status.equals(StatusSessaoVotacao.FECHADA)) {
            throw new RuntimeException("Sessão Está Fechada!");
        }
    }

    private void validaAssociado(String cpfAssociado, AssociadoService associadoService) {
        associadoService.validaAssociadoAptoVoto(cpfAssociado);
        validaVotoDuplicado(cpfAssociado);
    }

    private void validaVotoDuplicado(String cpfAssociado) {
        if (this.votos.containsKey(cpfAssociado)) {
           throw  new RuntimeException("Associado ja Votou Nessa Sessão.");
        }
    }

    private void atualizStatus() {
        if (this.status.equals(StatusSessaoVotacao.ABERTA)) {
            if (LocalDateTime.now().isAfter(this.momentoEncerramento)) {
                fechaSessao();
            }
        }
    }

    private void fechaSessao() {
        this.status = StatusSessaoVotacao.FECHADA;
    }

    public Long getTotalVotos() {
        return Long.valueOf(this.votos.size());
    }

    public Long getTotalSim() {
        return calculaVotosPorOpcao(OpcaoVoto.SIM);

    }
    public Long getTotalNao() {
        return calculaVotosPorOpcao(OpcaoVoto.NAO);
    }


    private Long calculaVotosPorOpcao(OpcaoVoto opcao ) {
        return votos.values().stream()
                .filter(voto -> voto.opcaoIgual(opcao))
                .count();
    }

    }


