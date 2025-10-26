/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Tests;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author davig
 */

@Controller
@RequestMapping("/teste2")
public class Test2Controller {

    @GetMapping
    @ResponseBody
    public String teste2Get() {
        System.out.println("Executando Teste 2 GET");
        return "Teste 2 GET OK!";
    }

    @PostMapping
    @ResponseBody
    public String teste2Post() {
        System.out.println("Executando Teste 2 POST");
        return "Teste 2 POST OK!";
    }
}

