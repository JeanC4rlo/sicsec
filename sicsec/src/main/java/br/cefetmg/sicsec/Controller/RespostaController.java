package br.cefetmg.sicsec.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.Service.CorrecaoService;

@RestController
@CrossOrigin(origins = "*")
public class RespostaController {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    CorrecaoService correcaoService;

    @PostMapping("/salvar/resposta")
    public ResponseEntity<?> salvarResposta(@RequestBody Resposta resposta) {
        try {
            correcaoService.corrigir(resposta);
            Resposta nova = respostaRepository.save(resposta);
            return ResponseEntity.ok(nova);
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao processar correção: " + e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao processar correção: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro inesperado ao salvar resposta: " + e.getMessage());
        }
    }
}
