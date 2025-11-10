package br.cefetmg.sicsec.Controller;

import java.util.List;

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

    @PostMapping("/curso/{cursoId}")
    @ResponseBody
    public List<Object> getAlunosByCurso(
        @PathVariable Long cursoId) {


        List<Aluno> alunos = usuarioService.getAlunosByCurso(cursoId);
        
        List<Object> alunosMap = new java.util.ArrayList<>();
        for (Aluno aluno : alunos) {
            java.util.Map<String, Object> alunoMap = java.util.Map.of(
                "id", aluno.getId(),
                "nome", aluno.getMatricula().getNome(),
                "numeroMatricula", aluno.getMatricula().getNumeroMatricula()
            );
            alunosMap.add(alunoMap);
        }

        return alunosMap;

    }

}
