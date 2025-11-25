package br.cefetmg.sicsec.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Service.DesempenhoService;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;
import br.cefetmg.sicsec.dto.DesempenhoDTO;
import br.cefetmg.sicsec.dto.NotaDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
@CrossOrigin("*")
public class DesempenhoController {
    @Autowired
    private DesempenhoService desempenhoService;

    @GetMapping("/desempenhos")
    public ResponseEntity<List<DadosRespostaAlunoDTO>> getDesempenhos() {
        return ResponseEntity.ok(desempenhoService.getListaDesempenhos());
    }

    @PatchMapping("/atribuir/{desempenhoId}/nota")
    public ResponseEntity<Desempenho> atribuirNota(@PathVariable Long desempenhoId, @RequestBody NotaDTO dto) {
        return ResponseEntity.ok(desempenhoService.atribuirNota(desempenhoId, dto.nota()));
    }
}
