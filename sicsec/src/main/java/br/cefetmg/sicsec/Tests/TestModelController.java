/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Tests;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author davig
 */
    
@Controller
@RequestMapping("/model")
public class TestModelController {

    @Autowired
    private TestModelService mService;
    
    @GetMapping("/usuario")
    @ResponseBody
    public void testModel(@RequestBody Map<String, Object> body,
            HttpServletResponse response) throws IOException {
        
        System.out.println("Executando Teste de Model: Aluno");
        
        UsuarioModel user = mService.ReceberUsuario(body);
        
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", "sucesso");
        resposta.put("mensagem", "Usuario " + user.getMatricula().getNome() + " criado.");
        resposta.put("User-" + user.getMatricula().getNome(), user);
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resposta);
        
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.setStatus(HttpServletResponse.SC_OK);
        
    }
    
}
