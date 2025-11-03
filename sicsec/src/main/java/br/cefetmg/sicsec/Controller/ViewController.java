package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    @GetMapping("/homeAdmin")
    public String homeAdmin() {
        return "homeAdmin";
    }
    
    @GetMapping("/homeProfessor")
    public String homeProfessor() {
        return "homeProfessor";
    }
    
    @GetMapping("/")
    public String index() {
        return "redirect:/auth/login";
    }
}