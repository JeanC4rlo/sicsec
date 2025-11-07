package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import br.cefetmg.sicsec.Model.Usuario.Aluno.*;

import br.cefetmg.sicsec.Service.UsuarioService;

@Controller
@RequestMapping("api/aluno")
public class AlunoController {
    
    @Autowired
    private UsuarioService usuarioService;
    //`/curso/${cursoId}`

    @PostMapping("/curso/${cursoId}")
    public @ResponseBody Iterable<Aluno> getAlunosByCurso(
        @PathVariable Long cursoId) {
        return usuarioService.getAlunosByCurso(cursoId);
    }

}
