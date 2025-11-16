package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

import br.cefetmg.sicsec.Service.LoginService;

@Controller
@RequestMapping("")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public String loginView() {
		return "redirect:/html/login/login.html";
	}

	@PostMapping("/api/login")
	public String login(@RequestParam String cpf,
			@RequestParam String senha,
			HttpServletRequest request,
			Model model) {

    	return loginService.authenticate(cpf, senha, request, model);
	}
}