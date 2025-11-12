package br.cefetmg.sicsec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {
    @GetMapping("/home")
    public String homeView(HttpSession session, RedirectAttributes redirectAttributes) {
        Object usuario = session.getAttribute("usuario");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Acesse a visão de aluno com um login válido!");
            return "redirect:/html/login/login.html";
        }

        return "redirect:/html/aluno/home.html";
    }

    @GetMapping("/homeAdmin")
    public String homeAdminView(HttpSession session, RedirectAttributes redirectAttributes) {
        Object usuario = session.getAttribute("usuario");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Acesse a visão de administrador com um login válido!");
            return "redirect:/html/login/login.html";
        }

        return "homeAdmin";
    }

    @GetMapping("/homeProfessor")
    public String homeProfessorView(HttpSession session, RedirectAttributes redirectAttributes) {
        Object usuario = session.getAttribute("usuario");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Acesse a visão de professor com um login válido!");
            return "redirect:/html/login/login.html";
        }

        return "homeProfessor";
    }

    @GetMapping("/")
    public String indexView() {
        return "redirect:/html/login/login.html";
    }

    @GetMapping("/fazerAtividades")
    public String getMethodName(@RequestParam String param) {
        return "/fazerAtividades";
    }
    
}