package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/home")
    public String homeView() {
        return "home";
    }
    
    @GetMapping("/homeAdmin")
    public String homeAdminView() {
        return "homeAdmin";
    }
    
    @GetMapping("/homeProfessor")
    public String homeProfessorView() {
        return "homeProfessor";
    }
    
    @GetMapping("/")
    public String indexView() {
        return "redirect:/login";
    }
}