/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;

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
    
    @PostMapping("/registrar")
    public String registrar(
            @RequestParam(name="alunos") List<Long> discentes,
            @RequestParam(name="professores") List<Long> doscentes,
            @RequestParam Long disciplina,
            @RequestParam Long curso,
            @RequestParam String nome,
            HttpServletRequest request) 
            throws IOException {

        System.out.println("Registrando turma com os seguintes dados:");
        System.out.println("Nome: " + nome); 
        System.out.println("Disciplina ID: " + disciplina);
        System.out.println("Curso ID: " + curso);
        System.out.println("Discentes IDs: " + discentes);
        System.out.println("Doscentes IDs: " + doscentes);
        
        Turma turma = tService.registrarTurma(nome, disciplina, curso, discentes, doscentes);

        System.out.println("Turma registrada com sucesso: " + turma.getNome());

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
    public ResponseEntity<List<Object>> getTurmasPorDepartamento(
        @PathVariable Long id
    ) {

        List<Object> turmas = tService.listarTurmasPorDepartamento(id);
        
        return ResponseEntity.ok(turmas);

    }
    
    @PostMapping("/buscar/{termo}")
    public ResponseEntity<List<Object>> buscarTurma(
            @PathVariable String termo,
            HttpServletRequest request,
            HttpServletResponse response) 
            throws IOException {
        
        List<Object> turmas = tService.buscarTurmaPorNome(termo, request);
        
        return ResponseEntity.ok(turmas);
        
    }
    
}
