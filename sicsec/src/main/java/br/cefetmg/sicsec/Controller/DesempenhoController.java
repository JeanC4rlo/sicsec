package br.cefetmg.sicsec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Service.DesempenhoService;
import br.cefetmg.sicsec.Service.RespostaService;
import br.cefetmg.sicsec.dto.DesempenhoComDadosAlunoDTO;
import br.cefetmg.sicsec.dto.DesempenhoDTO;
import br.cefetmg.sicsec.dto.NotaDTO;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/desempenho")
public class DesempenhoController {
    @Autowired
    private DesempenhoService desempenhoService;

    @Autowired
    private RespostaService respostaService;

    @GetMapping("/desempenhos")
    public ResponseEntity<List<DesempenhoComDadosAlunoDTO>> getDesempenhos(HttpSession session) {
        return ResponseEntity.ok(desempenhoService.getListaDesempenhos(session));
    }

    @GetMapping("/{desempenhoId}")
    public ResponseEntity<DesempenhoDTO> getDesempenho(@PathVariable Long desempenhoId) {
        return ResponseEntity.ok(respostaService.getDesempenhoDTO(desempenhoId));
    }

    @GetMapping("atividade/{atividadeId}/aluno/{alunoId}/nota")
    public ResponseEntity<Double> getMaiorNotaAtividade(@PathVariable Long atividadeId, @PathVariable Long alunoId) {
        Double nota = desempenhoService.getMaiorNotaAtividade(atividadeId, alunoId);
        return ResponseEntity.ok(nota);
    }

    @GetMapping("/tentativa/{tentativaId}/nota")
    public ResponseEntity<Double> getNotaTentativa(@PathVariable Long tentativaId) {
        Double nota = desempenhoService.getNotaTentativa(tentativaId);
        return ResponseEntity.ok(nota);
    }
    
    @PatchMapping("/{desempenhoId}/nota")
    public ResponseEntity<Desempenho> atribuirNota(@PathVariable Long desempenhoId, @RequestBody NotaDTO dto) {
        return ResponseEntity.ok(desempenhoService.atribuirNota(desempenhoId, dto.nota()));
    }
}
