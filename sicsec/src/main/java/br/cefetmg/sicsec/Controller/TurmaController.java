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
    public String atualizar(
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
    public String registrar(
            @RequestParam(name="alunos") List<Long> discentes,
            @RequestParam(name="professores") List<Long> doscentes,
            @RequestParam int anoLetivo,
            @RequestParam Long disciplina,
            @RequestParam Long curso,
            @RequestParam String nome,
            HttpServletRequest request) 
            throws IOException {
        
        tService.registrarTurma(nome, anoLetivo, disciplina, curso, discentes, doscentes);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

    @PostMapping("/getAll")
    public ResponseEntity<List<Object>> getAllTurmas(
            HttpServletRequest request) 
            throws IOException {
        
        List<Object> turmas = tService.listarTurmas();
        
        return ResponseEntity.ok(turmas);
        
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> getTurmaPorId(
        @PathVariable Long id
    ) {

        Object turma = tService.obterTurmaPorId(id);
        
        System.out.println("Turma obtida: " + turma.toString());

        return ResponseEntity.ok(turma);

    }
    

    @PostMapping("/curso/{id}")
    public ResponseEntity<List<Object>> getTurmasPorCurso(
        @PathVariable Long id
    ) {

        List<Object> turmas = tService.listarTurmasPorCurso(id);
        
        return ResponseEntity.ok(turmas);

    }

    @PostMapping("/departamento/{id}")
    public ResponseEntity<Object> getTurmasPorDepartamento(
        @PathVariable Long id
    ) {

        List<Turma> turmas = tService.listarTurmasPorDepartamento(id);
        List<Map<String, Object>> turmasMap = new ArrayList<>();

        for (Turma turma : turmas) {
            Map<String, Object> turmaMap = Map.of(
                "nome", turma.getNome(),
                "id", turma.getId(),
                "curso", turma.getCurso().getNome(),
                "cursoId", turma.getCurso().getId(),
                "ativo", turma.isAtivo()
            );
            turmasMap.add(turmaMap);
        }

        return ResponseEntity.ok(turmasMap);

    }
    
    @PostMapping("/buscar/{termo}")
    public ResponseEntity<List<Map<Integer, String>>> buscarTurma(
            @PathVariable String termo,
            HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException {
        
        List<Object> turmas = tService.buscarTurmaPorNome(termo, request);
        
        List<Map<Integer, String>> turmasMap = turmas.stream().map(turmaObj -> {
            Turma turma = (Turma) turmaObj;
            return Map.of(
                0, "Nome: " + turma.getNome(),
                1, "ID: " + turma.getId(),
                2, "Ativo: " + turma.isAtivo()
            );
        }).toList();

        return ResponseEntity.ok(turmasMap);
        
    }
    
}
