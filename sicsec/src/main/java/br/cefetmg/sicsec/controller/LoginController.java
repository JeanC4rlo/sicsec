package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import br.cefetmg.sicsec.service.LoginService;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginView() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cpf,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {
                          if (cpf == null || cpf.trim().isEmpty() || senha == null || senha.isEmpty()) {
                            model.addAttribute("error", "CPF e senha são obrigatórios.");
                            return "login";
                          }

                          cpf = cpf.replaceAll("[^0-9]", "");
                          Long cpfFormatado = Long.parseLong(cpf);

                          try {
                            UsuarioModel usuario = loginService.authenticate(cpfFormatado, senha);
                            if (usuario != null) {
                                session.setAttribute("usuario", usuario);
                                String redirectPage;
                                switch (usuario.getCargo()) {
                                    case ADMINISTRADOR:
                                        redirectPage = "redirect:/homeAdimin";
                                        break;
                                    case PROFESSOR:
                                        redirectPage = "redirect:/homeProfessor";
                                        break;
                                    default:
                                        redirectPage = "redirect:/home";
                                }
                                return redirectPage;
                            } else {
                                model.addAttribute("error", "CPF ou senha inválidos.");
                                return "login";
                            }
                          } catch (Exception e) {
                            model.addAttribute("error", "Error ao realizar login.");
                            return "login";
                          }
                        }
}