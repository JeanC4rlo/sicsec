package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import br.cefetmg.sicsec.Service.UsuarioService;


@Controller
@RequestMapping("api/professor")
public class ProfessorController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    //`/api/professor/disciplina/${disciplinaId}`

    @PostMapping("/disciplina/{disciplinaId}")
    public @ResponseBody Iterable<br.cefetmg.sicsec.Model.Usuario.Professor.Professor> getProfessoresByDisciplina(
        @org.springframework.web.bind.annotation.PathVariable Long disciplinaId) {
        
        return usuarioService.getProfessoresByDisciplina(disciplinaId);
        
    }

}
