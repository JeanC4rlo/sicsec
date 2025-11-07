/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Service.TurmaService;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author davig
 */
@RestController
@RequestMapping("/api/turmas")
public class GerenciarTurmaController {
    
    @Autowired
    private TurmaService tService;
    
    @PostMapping("/registrar")
    public void registrar(
            @RequestParam Long disciplina,
            @RequestParam Long curso,
            @RequestParam List<Long> discentes,
            @RequestParam List<Long> doscentes,
            @RequestParam String nome,
            HttpServletResponse response) 
            throws IOException {
        
        Turma turma = tService.registrarTurma(nome, disciplina, curso, discentes, doscentes);
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(turma);
        
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
        
    }
    
}
