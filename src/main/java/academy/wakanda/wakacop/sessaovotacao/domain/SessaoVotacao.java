package academy.wakanda.wakacop.sessaovotacao.domain;

import academy.wakanda.wakacop.sessaovotacao.application.api.SessaoAberturaRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private UUID pauta;
    private Integer tempoDuracao;
    private LocalDateTime dataAbertura;

    public SessaoVotacao(SessaoAberturaRequest sessaoAberturaRequest) {
        this.pauta = sessaoAberturaRequest.getPauta();
        this.tempoDuracao = sessaoAberturaRequest.getTempoduracao().orElse(1);
        this.dataAbertura = LocalDateTime.now();
    }
}
