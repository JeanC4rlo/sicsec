package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepo usuarioRepo;

	@Autowired
	private SessionService sessionService;

    public Usuario register(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public String authenticate(String cpf,
                               String senha,
                               HttpServletRequest request,
                               Model model) {

        if (cpf == null || cpf.trim().isEmpty() || senha == null || senha.isEmpty()) {
			model.addAttribute("error", "CPF e senha são obrigatórios.");
			return "redirect:/html/login/login.html";
		}

		cpf = cpf.replaceAll("[^0-9]", "");
		Long cpfFormatado = Long.parseLong(cpf);

		try {
			Usuario usuario = usuarioRepo.findByCpf(cpfFormatado);

            if (usuario == null) {
                throw new EntityNotFoundException();
            }

            if (senha.equals(usuario.getSenha()) && usuario != null) {
				HttpSession session = request.getSession(true);
				sessionService.salvarDadosSessao(session, usuario);
				String redirectPage;
				switch (usuario.getCargo()) {
					case ADMINISTRADOR:
						redirectPage = "redirect:/html/admin/home.html";
						break;
					case PROFESSOR:
						redirectPage = "redirect:/html/professor/home.html";
						break;
					default:
						redirectPage = "redirect:/html/aluno/home.html";
				}
				return redirectPage;
			} else {
				model.addAttribute("error", "CPF ou senha inválidos.");
				return "redirect:/html/login/login.html";
			}
		} catch(EntityNotFoundException e) {
			model.addAttribute("error", "Usuário não encontrado.");
			return "redirect:/html/login/login.html";
		} catch (Exception e) {
			model.addAttribute("error", "Error ao realizar login.");
			return "redirect:/html/login/login.html";
		}
    }
}
