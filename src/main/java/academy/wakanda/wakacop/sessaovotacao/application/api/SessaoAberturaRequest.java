package academy.wakanda.wakacop.sessaovotacao.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.UUID;
@Getter
@ToString
public class SessaoAberturaRequest {
    @NotNull
    private UUID idPauta;
    private Integer tempoDuracao;

    public Optional<Integer> getTempoduracao(){
        return Optional.ofNullable(this.tempoDuracao);
    }
}
