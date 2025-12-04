package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import br.cefetmg.sicsec.Service.LoginService;

@Controller
@RequestMapping("")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@GetMapping("/")
    public String indexPage() {
        return "redirect:/login";
    }

	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, Model model) {
		String error = (String) request.getSession().getAttribute("error");
		if (error != null) {
			model.addAttribute("error", error);
			request.getSession().removeAttribute("error");
		}
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String cpf,
			@RequestParam String senha,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		return loginService.authenticate(cpf, senha, session, redirectAttributes);
	}

	@PostMapping("/login/select")
	public String loginSelect(@RequestParam Long idUsuario,
							  @RequestParam(required = false) String senha,
							  HttpSession session,
							  RedirectAttributes redirectAttributes) {
		return loginService.selectPerfil(idUsuario, senha, session, redirectAttributes);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		return loginService.logout(session);
	}
}
