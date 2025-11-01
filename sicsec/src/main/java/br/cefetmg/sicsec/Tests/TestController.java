/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author davig
 */
@RestController
@RequestMapping("")
public class TestController {
    
    @Autowired
    private TestService tService;
    
    @PostMapping("/test")
    public void logar(@RequestBody Map<String, Object> body, 
            HttpServletResponse response) throws IOException {
        
        String cpf = (String) body.get("cpf");
        String senha = (String) body.get("senha");
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tService.login(cpf, senha));
        
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
        
    }
    
    @PostMapping("/criarBD")
    public void criaBD(HttpServletResponse response) throws IOException {
    
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tService.criaBD());
        
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
        
    }
    
}
