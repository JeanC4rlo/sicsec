package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepo usuarioRepo;

    public Usuario register(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public String authenticate(String cpf,
                               String senha,
                               HttpSession session,
                               Model model) {

        if (cpf == null || cpf.trim().isEmpty() || senha == null || senha.isEmpty()) {
			model.addAttribute("error", "CPF e senha são obrigatórios.");
			return "login";
		}

		cpf = cpf.replaceAll("[^0-9]", "");
		Long cpfFormatado = Long.parseLong(cpf);

		try {
			Usuario usuario = usuarioRepo.findByCpf(cpfFormatado);

            if (usuario == null) {
                throw new EntityNotFoundException();
            }

            if (senha.equals(usuario.getSenha()) && usuario != null) {
				session.setAttribute("usuario", usuario);
				String redirectPage;
				switch (usuario.getCargo()) {
					case ADMINISTRADOR:
						redirectPage = "redirect:/homeAdmin";
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
		} catch(EntityNotFoundException e) {
			model.addAttribute("error", "Usuário não encontrado.");
			return "login";
		} catch (Exception e) {
			model.addAttribute("error", "Error ao realizar login.");
			return "login";
		}
    }
}
