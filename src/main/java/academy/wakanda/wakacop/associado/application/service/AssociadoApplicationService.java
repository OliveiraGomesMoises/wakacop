package academy.wakanda.wakacop.associado.application.service;

import academy.wakanda.wakacop.associado.infra.client.ConsultaCpfResponse;
import academy.wakanda.wakacop.associado.infra.client.SerproClientFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssociadoApplicationService implements AssociadoService {
    private final SerproClientFeign serproClientFeign;
    @Override
    public void validaAssociadoAptoVoto(String cpfAssociado) {
        log.debug("[start] AssociadoApplicationService - validaAssociadoAptoVoto");
        ConsultaCpfResponse consultaCPFResponse = serproClientFeign.consultaCPF(TOKEN, cpfAssociado);
        valida(consultaCPFResponse);
        log.debug("[finish] AssociadoApplicationService - validaAssociadoAptoVoto");
    }

    private void valida(ConsultaCpfResponse consultaCPFResponse) {
        if(consultaCPFResponse.isInvalid()){
            throw new RuntimeException("CPF Associado Inválido");
        }
    }
    private static final String TOKEN = "Bearer 1539dcc2-f944-4d6f-b905-5a7bab1d23bf";
}
