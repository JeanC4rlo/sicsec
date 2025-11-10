/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Service.TurmaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author davig
 */
@Controller
@RequestMapping("/api/turma")
public class TurmaController {
    
    @Autowired
    private TurmaService tService;
    
    @PostMapping("/atualizar")
    public String atualizarTurma(
            @RequestParam Long id,
            @RequestParam String nome,
            @RequestParam(name="alunos") List<Long> discentes,
            @RequestParam(name="professores") List<Long> doscentes,
            @RequestParam(required=false, defaultValue="false") boolean arquivar,
            HttpServletRequest request) 
            throws IOException {

        tService.atualizarTurma(id, nome, discentes, doscentes, !arquivar);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

    @PostMapping("/registrar")
    public String registrarTurma(
            @RequestParam(name="alunos") List<Long> discentes,
            @RequestParam(name="professores") List<Long> doscentes,
            @RequestParam Long disciplina,
            @RequestParam Long curso,
            @RequestParam String nome,
            HttpServletRequest request) 
            throws IOException {
        
        tService.registrarTurma(nome, 2025, disciplina, curso, discentes, doscentes);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

    @PostMapping("/getAll")
    public ResponseEntity<List<Object>> getAllTurmas(
            HttpServletRequest request) 
            throws IOException {
        
        List<Turma> turmas = tService.listarTurmas();
        List<Object> turmasMap = new ArrayList<>();

        for(Turma turma : turmas) {
            turmasMap.add(Map.of(
                "id", turma.getId(),
                "nome", turma.getNome(),
                "disciplina", Map.of(
                    "id", turma.getDisciplina().getId(),
                    "nome", turma.getDisciplina().getNome()
                ),
                "curso", Map.of(
                    "id", turma.getCurso().getId(),
                    "nome", turma.getCurso().getNome()
                ),
                "ativo", turma.isAtivo()
            ));
        }

        return ResponseEntity.ok(turmasMap);
        
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> getTurmaPorId(
        @PathVariable Long id
    ) {

        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body("Turma não encontrada");
        }

        List<Long> discentesId = new ArrayList<>();
        for (Aluno a : turma.getDiscentes()) {
            discentesId.add(a.getId());
        }

        List<Long> doscentesId = new ArrayList<>();
        for (Professor p : turma.getDoscentes()) { 
            doscentesId.add(p.getId());
        }

        Map<String, Object> turmaMap = Map.of(
                "id", turma.getId(),
                "nome", turma.getNome(),
                "disciplina", Map.of(
                    "id", turma.getDisciplina().getId(),
                    "nome", turma.getDisciplina().getNome()
                ),
                "curso", Map.of(
                    "id", turma.getCurso().getId(),
                    "nome", turma.getCurso().getNome()
                ),
                "ativo", turma.isAtivo(),
            "professores", doscentesId,
            "alunos", discentesId
        );

        return ResponseEntity.ok(turmaMap);

    }
    

    @PostMapping("/curso/{id}")
    public ResponseEntity<List<Object>> getTurmasPorCurso(
        @PathVariable Long id
    ) {

        List<Turma> turmas = tService.listarTurmasPorCurso(id);

        List<Object> turmasMap = new ArrayList<>();

        for(Turma turma : turmas) {
            turmasMap.add(Map.of(
                "id", turma.getId(),
                "nome", turma.getNome(),
                "disciplina", Map.of(
                    "id", turma.getDisciplina().getId(),
                    "nome", turma.getDisciplina().getNome()
                ),
                "curso", Map.of(
                    "id", turma.getCurso().getId(),
                    "nome", turma.getCurso().getNome()
                ),
                "ativo", turma.isAtivo()
            ));
        }

        return ResponseEntity.ok(turmasMap);

    }

    @PostMapping("/departamento/{id}")
    public ResponseEntity<Object> getTurmasPorDepartamento(
        @PathVariable Long id
    ) {

        List<Turma> turmas = tService.listarTurmasPorDepartamento(id);
        List<Map<String, Object>> turmasMap = new ArrayList<>();

        for (Turma turma : turmas) {
            Map<String, Object> turmaMap = Map.of(
                "id", turma.getId(),
                "nome", turma.getNome(),
                "disciplina", Map.of(
                    "id", turma.getDisciplina().getId(),
                    "nome", turma.getDisciplina().getNome()
                ),
                "curso", Map.of(
                    "id", turma.getCurso().getId(),
                    "nome", turma.getCurso().getNome()
                ),
                "ativo", turma.isAtivo()
            );
            turmasMap.add(turmaMap);
        }

        return ResponseEntity.ok(turmasMap);

    }
    
    @PostMapping("/buscar/{termo}")
    public ResponseEntity<List<Object>> buscarTurma(
            @PathVariable String termo,
            HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException {
        
        List<Turma> turmas = tService.buscarTurmaPorNome(termo, request);
        
        List<Object> turmasMap = new ArrayList<>();

        for(Turma turma : turmas) {
            turmasMap.add(Map.of(
                "id", turma.getId(),
                "nome", turma.getNome(),
                "disciplina", Map.of(
                    "id", turma.getDisciplina().getId(),
                    "nome", turma.getDisciplina().getNome()
                ),
                "curso", Map.of(
                    "id", turma.getCurso().getId(),
                    "nome", turma.getCurso().getNome()
                ),
                "ativo", turma.isAtivo()
            ));
        }

        return ResponseEntity.ok(turmasMap);
        
    }
    
}
