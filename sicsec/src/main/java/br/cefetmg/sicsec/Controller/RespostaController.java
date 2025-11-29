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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Exceptions.CorrecaoException;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Service.RespostaService;
import br.cefetmg.sicsec.Service.ValidarArquivosService;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/resposta")
public class RespostaController {
    @Autowired
    RespostaService respostaService;

    @Autowired
    ValidarArquivosService validarArquivosService;

    @GetMapping("/respostas")
    public ResponseEntity<List<Resposta>> getListRespostas() {
        List<Resposta> listaRespostas = respostaService.getListRespostas();
        return ResponseEntity.ok(listaRespostas);
    }

    @GetMapping("/{respostaId}")
    public ResponseEntity<Resposta> getResposta(@PathVariable Long respostaId) {
        return ResponseEntity.ok(respostaService.getResposta(respostaId));
    }

    @PostMapping("/enviar/questionario")
    public ResponseEntity<?> corrigirResposta(@RequestBody Resposta resposta) throws CorrecaoException {
        Resposta nova = respostaService.salvarQuestionario(resposta);
        return ResponseEntity.ok(nova);
    }

    @PostMapping("/enviar/redacao")
    public ResponseEntity<Resposta> salvarRedacao(@RequestBody Resposta resposta) {
        Resposta nova = respostaService.salvarRedacao(resposta);
        return ResponseEntity.ok(nova);
    }

    @PostMapping("/enviar/arquivo")
    public ResponseEntity<Resposta> salvarArquivo(
            @RequestPart("resposta") Resposta resposta,
            @RequestPart("arquivo") MultipartFile arquivo) throws IOException {
        return ResponseEntity.ok(respostaService.salvarEnvioArquivo(resposta, arquivo));
    }

    @GetMapping("/dados/{desempenhoId}")
    public ResponseEntity<DadosRespostaAlunoDTO> getDadosResposta(@PathVariable Long desempenhoId) {
        return ResponseEntity.ok(respostaService.getDadosResposta(desempenhoId));
    }
}
