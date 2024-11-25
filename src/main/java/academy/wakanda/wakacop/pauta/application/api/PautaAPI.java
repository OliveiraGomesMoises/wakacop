package academy.wakanda.wakacop.pauta.application.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PautaAPI {
     @PostMapping
     PautaCadastradaResponse cadastraPauta(@RequestBody NovaPautaRequest novaPautaRequest);

}