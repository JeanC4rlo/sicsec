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
import br.cefetmg.sicsec.dto.DesempenhoDTO;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/resposta")
public class RespostaController {
    @Autowired
    RespostaService respostaService;

    @GetMapping("/respostas")
    public ResponseEntity<List<Resposta>> getListRespostas() {
        List<Resposta> listaRespostas = respostaService.getListRespostas();
        return ResponseEntity.ok(listaRespostas);
    }

    @GetMapping("/{respostaId}")
    public ResponseEntity<Resposta> getResposta(@PathVariable Long respostaId) {
        return ResponseEntity.ok(respostaService.getResposta(respostaId));
    }

    @GetMapping("/dados/{desempenhoId}")
    public ResponseEntity<DesempenhoDTO> getDadosResposta(@PathVariable Long desempenhoId) {
        return ResponseEntity.ok(respostaService.getDesempenhoDTO(desempenhoId));
    }

    @PostMapping("/enviar")
    public ResponseEntity<?> salvarResposta(@RequestBody Resposta resposta, HttpSession session)
            throws CorrecaoException {
        Resposta nova = respostaService.salvarResposta(resposta, session);
        return ResponseEntity.ok(nova);
    }

    @PostMapping("/enviar/arquivo")
    public ResponseEntity<Resposta> salvarRespostaComArquivo(
            @RequestPart("resposta") Resposta resposta,
            @RequestPart("arquivo") MultipartFile arquivo,
            HttpSession session) throws IOException {
        return ResponseEntity.ok(respostaService.salvarRespostaComArquivo(resposta, arquivo, session));
    }
}
