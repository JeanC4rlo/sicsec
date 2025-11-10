package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("api/professor")
public class ProfessorController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/disciplina/{disciplinaId}")
    @ResponseBody 
    public Object getProfessoresByDisciplina(
        @PathVariable Long disciplinaId) {
        
        List<Professor> professores = usuarioService.getProfessoresByDisciplina(disciplinaId);

        List<Map<String, Object>> professoresMap = new ArrayList<>();
        
        for (Professor prof : professores) {
            Map<String, Object> profMap = Map.of(
                "id", prof.getId(),
                "nome", prof.getMatricula().getNome(),
                "numeroMatricula", prof.getMatricula().getNumeroMatricula()
            );
            professoresMap.add(profMap);
        }

        return professoresMap;

    }

}
