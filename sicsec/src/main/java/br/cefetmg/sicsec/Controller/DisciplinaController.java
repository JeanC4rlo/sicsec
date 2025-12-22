package br.cefetmg.sicsec.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Service.DisciplinaService;
import br.cefetmg.sicsec.Dto.DisciplinaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("api/disciplina")
public class DisciplinaController {
        
    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/curso/{cursoId}")
    public @ResponseBody List<Disciplina> getDisciplinasByCurso(@PathVariable Long cursoId) {
        return disciplinaService.getByCursoId(cursoId);
    }
    
    @PostMapping("/salvar")
    public ResponseEntity<String> salvar(@RequestBody DisciplinaDTO dto) {
        try {
            disciplinaService.salvarComMatriculas(dto);
            return ResponseEntity.ok("Disciplina registrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar registrar " + e.getMessage());
        }
    }
    
    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody DisciplinaDTO dto) {
        try {
            disciplinaService.salvarComMatriculas(dto); 
            return ResponseEntity.ok("Disciplina atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar disciplina: " + e.getMessage());
        }
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<Disciplina> getByCodigo(@PathVariable String codigo) {
        return disciplinaService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}