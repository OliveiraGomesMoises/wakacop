package academy.wakanda.wakacop.sessaovotacao.application.infra;

import academy.wakanda.wakacop.sessaovotacao.domain.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessaoVotacaoSpringDataJPARepository extends JpaRepository<SessaoVotacao, UUID> {
}
