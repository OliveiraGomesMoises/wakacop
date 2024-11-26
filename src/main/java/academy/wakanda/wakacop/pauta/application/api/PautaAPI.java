package academy.wakanda.wakacop.pauta.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public interface PautaAPI {
     @ResponseStatus(HttpStatus.CREATED)
     @PostMapping
     PautaCadastradaResponse cadastraPauta(@RequestBody NovaPautaRequest novaPautaRequest);

}
