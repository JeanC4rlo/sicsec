package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Service.DisciplinaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("api/disciplina")
public class DisciplinaController {
        
    @Autowired
    private DisciplinaService disciplinaService;
    
    @PostMapping("/curso/{cursoId}")
    public @ResponseBody ResponseEntity<Object> getDisciplinasByCurso(@PathVariable Long cursoId) {
        List<Disciplina> disciplinas = disciplinaService.getByCursoId(cursoId);

        List<Map<String, Object>> disciplinasMap = new ArrayList<>();

        for(Disciplina disciplina : disciplinas) {

            Map<String, Object> disciplinaMap = new HashMap<>();
            disciplinaMap.put("id", disciplina.getId());
            disciplinaMap.put("nome", disciplina.getNome());
            disciplinasMap.add(disciplinaMap);

        }

        return ResponseEntity.ok(disciplinasMap);

    }

}
