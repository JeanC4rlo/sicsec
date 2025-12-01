package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Exceptions.CorrecaoException;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.Service.CalculoNotaService;
import br.cefetmg.sicsec.Service.CorrecaoService;

@RestController
@CrossOrigin(origins = "*")
public class RespostaController {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    CorrecaoService correcaoService;

    @Autowired
    CalculoNotaService calculoNotaService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @PostMapping("/salvar/resposta")
    public ResponseEntity<?> corrigirResposta(@RequestBody Resposta resposta) {
        try {
        correcaoService.corrigir(resposta);
        Resposta nova = respostaRepository.save(resposta);
        return ResponseEntity.ok(nova);
        } catch(CorrecaoException e) {
            return globalExceptionHandler.handleCorrecao(e);
        }
    }

    @GetMapping("/conferir-nota/{atividadeId}/{statusAtividadeId}")
    public double conferirNota(@PathVariable Long atividadeId, @PathVariable Long statusAtividadeId) {
        return calculoNotaService.calcular(atividadeId, statusAtividadeId);
    }
}
