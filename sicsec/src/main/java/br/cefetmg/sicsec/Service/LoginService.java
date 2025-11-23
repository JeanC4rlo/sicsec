package br.cefetmg.sicsec.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import br.cefetmg.sicsec.dto.Perfil;
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
			RedirectAttributes redirectAttributes) {

		if (cpf == null || cpf.trim().isEmpty() || senha == null || senha.isEmpty()) {
			redirectAttributes.addAttribute("error", "CPF e senha são obrigatórios.");
			return "redirect:/login";
		}

		cpf = cpf.replaceAll("[^0-9]", "");
		Long cpfFormatado = Long.parseLong(cpf);

		try {
			List<Usuario> usuariosEncontrados = usuarioRepo.findByCpf(cpfFormatado);

			if (usuariosEncontrados == null || usuariosEncontrados.isEmpty()) {
    			redirectAttributes.addAttribute("error", "Nenhum usuário não encontrado vinculado a esse CPF!");
    			return "redirect:/login";
			}

			List<Perfil> perfis = new ArrayList<>();
			boolean encontrado = false;

			for (Usuario usuario : usuariosEncontrados) {
				Perfil perfil = new Perfil(usuario, false);

				if (usuario.getSenha().equals(senha) && !encontrado) {
					encontrado = true;
					perfil.setLogado(true);
					session.setAttribute("perfilSelecionado", perfil);
				}

				perfis.add(perfil);
			}

			if(encontrado) {
				session.setAttribute("perfis", perfis);
				return "redirect:/home";
			}

			redirectAttributes.addAttribute("error", "CPF ou senha inválidos.");
			return "redirect:/login";
		} catch (EntityNotFoundException e) {
			redirectAttributes.addAttribute("error", "Usuário não encontrado.");
			return "redirect:/login";
		} catch (Exception e) {
			redirectAttributes.addAttribute("error", "Error ao realizar login.");
			return "redirect:/login";
		}
	}

	public String selectPerfil(Long idUsuario,
							   String senha,
							   HttpSession session,
							   RedirectAttributes redirectAttributes) {

		List<Perfil> perfis = (List<Perfil>) session.getAttribute("perfis");
		
		// Filtra os perfis com base no ID e checa se ele existe
		Perfil perfilSelecionado = perfis.stream()
            .filter(p -> p.getUsuario().getId().equals(idUsuario))
            .findFirst()
            .orElse(null);

		if (perfilSelecionado == null) {
			redirectAttributes.addFlashAttribute("error", "Perfil não encontrado.");
			return "redirect:/home";
		}

		Usuario usuario = perfilSelecionado.getUsuario();

		// Caso NÃO esteja logado, valida a senha
		if (!perfilSelecionado.getLogado()) {

			if (senha == null) {
				redirectAttributes.addFlashAttribute("error", "Insira a senha do perfil.");
				redirectAttributes.addAttribute("idUsuario", idUsuario);
				return "redirect:/home";
			}

			if (!usuario.getSenha().equals(senha)) {
				redirectAttributes.addFlashAttribute("error", "Senha incorreta.");
				redirectAttributes.addAttribute("idUsuario", idUsuario);
				return "redirect:/home";
			}

			perfilSelecionado.setLogado(true);
		}

		// Marca todos como deslogados, exceto o selecionado
		perfis.forEach(p -> p.setLogado(false));
		perfilSelecionado.setLogado(true);

		// Atualiza os dados de sessão
		session.setAttribute("perfilSelecionado", perfilSelecionado);
		session.setAttribute("perfis", perfis);

		return "redirect:/home";
	}

	public String logout(HttpSession session) {
		session.setAttribute("perfilSelecionado", null);
		session.setAttribute("perfis", null);
		session.invalidate();
		return "redirect:/";
	}
}
