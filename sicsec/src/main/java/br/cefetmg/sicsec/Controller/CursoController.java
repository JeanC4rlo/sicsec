package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Service.CursoService;

@Controller
@RequestMapping("api/curso")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @PostMapping("/getAll")
    public @ResponseBody Iterable<Curso> getAllCursos() {
        return cursoService.getAll();
    }

    @PostMapping("/departamento/{departamentoId}")
    public @ResponseBody Iterable<Curso> getCursosByDepartamento(
        @PathVariable Long departamentoId) {

        return cursoService.getCursosByDepartamento(departamentoId);
    }

    @PostMapping("/{cursoId}")
    public @ResponseBody Curso getCursoById(
        @PathVariable Long cursoId) {
        return cursoService.getCursoById(cursoId);
    }

}