package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("api/disciplina")
public class DisciplinaController {
        
    @Autowired
    private DisciplinaService disciplinaService;
    
    @PostMapping("/curso/{cursoId}")
    public @ResponseBody Iterable<Disciplina> getDisciplinasByCurso(@PathVariable Long cursoId) {
        return disciplinaService.getByCursoId(cursoId);
    }

}
