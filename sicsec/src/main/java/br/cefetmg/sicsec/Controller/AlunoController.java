package br.cefetmg.sicsec.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import br.cefetmg.sicsec.Model.Curso.Turma.presenca.Presenca;
import br.cefetmg.sicsec.Model.Usuario.Aluno.*;

import br.cefetmg.sicsec.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/frequencia/{id}")
    public ResponseEntity<List<Map<String, Object>>> getFrequenciaEmTurma(HttpSession session, @PathVariable Long id) {

        List<Presenca> presencas = new ArrayList<>();

        try {
            presencas = usuarioService.getFrequenciaEmTurma(session, id);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }

        List<Map<String, Object>> presencasMap = new java.util.ArrayList<>();
        for (Presenca presenca : presencas) {
            Map<String, Object> presencaMap = Map.of(
                "data", presenca.getLista().getData(),
                "presente", presenca.isPresente()
            );
            presencasMap.add(presencaMap);
        }

        return ResponseEntity.ok(presencasMap);

    }

}
