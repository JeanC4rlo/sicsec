/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import br.cefetmg.sicsec.Model.Curso.Aula;
import br.cefetmg.sicsec.Model.Curso.Turma.Noticia;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Service.TurmaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/listarAluno")
    public ResponseEntity<List<Object>> listarTurmasDeAluno(
        HttpSession session
    ) {

        try {

            System.out.println("Listando turmas de aluno...");

            List<Turma> turmas = tService.listarTurmasDeAluno(session);
         
            List<Object> turmasMap = new ArrayList<>();

            for(Turma turma : turmas) {

                if (turma == null) continue;
                if (!turma.isAtivo()) continue;

                turmasMap.add(Map.of(
                    "id", turma.getId(),
                    "nome", turma.getNome()
                ));

            }

            return ResponseEntity.ok(turmasMap);

        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(null);
        }

    }  

    @PostMapping("info/{id}")
    public ResponseEntity<Map<String, Object>> getTurmaInfo(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);

        if (turma == null) {
            return ResponseEntity.status(404).body(null);
        }

        Map<String, Object> turmaMap = new HashMap<>();
        turmaMap.putAll(Map.of(
                "nome", turma.getNome(),
                "disciplina", turma.getDisciplina().getNome()
        ));
        
        List<Aula> aulas = turma.getAulas();

        List<Map<String, Object>> aulasMap = new ArrayList<>();

        for (Aula aula : aulas) {

            aulasMap.add(Map.of(
                "dia", aula.getDia().toString(),
                "horaInicio", aula.getHorario().getInicio().toString(),
                "horaFim", aula.getHorario().getFim().toString(),
                "sala", aula.getSala()
            ));

        }

        if (aulasMap.isEmpty()) {
            aulasMap.add(Map.of(
                "dia", "Á definir",
                "horaInicio", "Á definir",
                "horaFim", "Á definir",
                "sala", "Á definir"
            ));
        }

        System.out.println(aulasMap);

        turmaMap.put("aulas", aulasMap);

        return ResponseEntity.ok(turmaMap);

    }

    @PostMapping("/participantes/{id}")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getParticipantesTurma(HttpSession session, @PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma != null) {
            List<Map<String, Object>> alunos = new ArrayList<>();
            for (Aluno aluno : turma.getDiscentes()) {
                alunos.add(Map.of(
                    "id", aluno.getId(),
                    "nome", aluno.getMatricula().getNome(),
                    "email", aluno.getMatricula().getEmail(),
                    "foto", aluno.getFotoPerfil()
                ));
            }

            List<Map<String, Object>> professores = new ArrayList<>();
            for (Professor professor : turma.getDoscentes()) {
                professores.add(Map.of(
                    "id", professor.getId(),
                    "nome", professor.getMatricula().getNome(),
                    "email", professor.getMatricula().getEmail(),
                    "foto", professor.getFotoPerfil()
                ));
            }

            return ResponseEntity.ok(Map.of(
                "docentes", alunos,
                "discentes", professores
            ));
        }
        
        return ResponseEntity.badRequest().body(null);
    }
    
    @PostMapping("/noticias/{id}")
    public ResponseEntity<List<Map<String, Object>>> getNoticiasTurma(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        
        List<Map<String, Object>> noticiasMap = new ArrayList<>();

        for (Noticia noticia : turma.getNoticias()) {
            noticiasMap.add(Map.of(
                "Autor", noticia.getAutor().getMatricula().getNome(),
                "data", noticia.getData().toString(),
                "Manchete", noticia.getManchete(),
                "Corpo", noticia.getCorpo()
            ));
        }

        return ResponseEntity.ok(noticiasMap);

    }

    @PostMapping("/atividades/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAtividadesTurma(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        
        List<Map<String, Object>> atividadesMap = new ArrayList<>();

        // TO DO: Implementar atividades quando o modelo estiver pronto

        return ResponseEntity.ok(null);

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
