package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Exceptions.CorrecaoException;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.Service.CalculoNotaService;
import br.cefetmg.sicsec.Service.CorrecaoService;
import br.cefetmg.sicsec.Service.RespostaService;
import br.cefetmg.sicsec.Service.ValidarArquivosService;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;

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
    RespostaService respostaService;

    @Autowired
    ValidarArquivosService validarArquivosService;

    @PostMapping("/enviar/questionario")
    public ResponseEntity<?> corrigirResposta(@RequestBody Resposta resposta) throws CorrecaoException {
        correcaoService.corrigir(resposta);
        Resposta nova = respostaRepository.save(resposta);
        return ResponseEntity.ok(nova);
    }

    @GetMapping("/conferir-nota/{atividadeId}/{statusAtividadeId}")
    public double conferirNota(@PathVariable Long atividadeId, @PathVariable Long statusAtividadeId) {
        return calculoNotaService.calcular(statusAtividadeId);
    }

    @GetMapping("/carregar/respostas")
    public List<Resposta> getRespostas() {
        return respostaRepository.findAll();
    }

    @GetMapping("/resposta/{respostaId}")
    public ResponseEntity<Resposta> getMethodName(@PathVariable Long respostaId) {
        return ResponseEntity.ok(respostaService.getResposta(respostaId));
    }

    @PostMapping("/enviar/redacao")
    public ResponseEntity<Resposta> salvarRedacao(@RequestBody Resposta resposta) {
        return ResponseEntity.ok(respostaService.salvarRedacao(resposta));
    }

    @PostMapping("/enviar/arquivo")
    public ResponseEntity<Resposta> salvarArquivo(
            @RequestPart("resposta") Resposta resposta,
            @RequestPart("arquivo") MultipartFile arquivo) throws IOException {
        return ResponseEntity.ok(respostaService.salvarEnvioArquivo(resposta, arquivo));
    }

    @GetMapping("/desempenho/{desempenhoId}")
    public ResponseEntity<DadosRespostaAlunoDTO> getDesempenho(@PathVariable Long desempenhoId) {
        return ResponseEntity.ok(respostaService.getDadosRespostaAluno(desempenhoId));
    }
}
