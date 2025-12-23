package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.dto.Secao;

@Service
public class HomeService {
	private static final Map<Cargo, List<Secao>> ROLE_SECTIONS = new HashMap<>();

	static {
		ROLE_SECTIONS.put(Cargo.ALUNO, Arrays.asList(
				new Secao(
						"/images/icons/homepage.svg",
						"/html/aluno/sections/homepage.html",
						"Página inicial",
						Arrays.asList(),
						Arrays.asList("/js/aluno/frag/homepage.js"),
						"homepage",
						true),
				new Secao(
						"/images/icons/check.svg",
						"/html/aluno/sections/atividades.html",
						"Atividades",
						Arrays.asList(),
						Arrays.asList("/js/aluno/frag/atividades.js"),
						"atividades",
						false),
				new Secao(
						"/images/icons/boletim.svg",
						"/html/aluno/sections/boletim.html",
						"Boletim",
						Arrays.asList("/css/homeAluno/boletim.css"),
						Arrays.asList("/js/aluno/frag/boletim.js"),
						"boletim",
						false),
				new Secao(
						"/images/icons/turmas.svg",
						"/html/aluno/sections/acesso-turmas.html",
						"Turmas",
						Arrays.asList("/css/homeAluno/turmas.css"),
						Arrays.asList(
							"/js/aluno/frag/turmas/turmaSections.js", 
							"/js/aluno/frag/turmas/turmasVinculadas.js",
							"/js/aluno/frag/turmas/initTurmasAluno.js"
						),
						"turmas",
						false),
				new Secao(
						"/images/icons/biblioteca.svg",
						"/html/aluno/sections/biblioteca.html",
						"Biblioteca",
						Arrays.asList(),
						Arrays.asList(),
						"biblioteca",
						false),
				new Secao(
						"/images/icons/assinaturas.svg",
						"/html/aluno/sections/assinaturas.html",
						"Assinaturas",
						Arrays.asList("/css/aluno/assinaturas.css"),
						Arrays.asList("/js/aluno/frag/assinaturas.js"),
						"assinaturas",
						false),
				new Secao(
						"/images/icons/bolsas.svg",
						"/html/aluno/sections/bolsas.html",
						"Bolsas",
						Arrays.asList(),
						Arrays.asList("/js/aluno/frag/bolsas.js"),
						"bolsas",
						false)));

		ROLE_SECTIONS.put(Cargo.PROFESSOR, Arrays.asList(
				new Secao(
						"/images/icons/homepage.svg",
						"/html/aluno/sections/homepage.html",
						"Página inicial",
						Arrays.asList(),
						Arrays.asList("/js/aluno/frag/homepage.js"),
						"homepage",
						true),
				new Secao(
						"/images/icons/materia.svg",
						"/html/professor/sections/atividades.html",
						"Atividades",
						Arrays.asList("/css/professor/atividades.css"),
						Arrays.asList("/js/professor/frag/atividades.js"),
						"atividades",
						false),
				new Secao(
						"/images/icons/materia.svg",
						"/html/professor/sections/turmas.html",
						"Turmas",
						Arrays.asList("/css/professor/turma.css"),
						Arrays.asList("/js/professor/frag/turmas.js"),
						"turmas",
						false),
				new Secao(
						"/images/icons/assinaturas.svg",
						"/html/professor/sections/assinaturas.html",
						"Assinaturas",
						Arrays.asList("/css/professor/assinaturas.css"),
						Arrays.asList("/js/professor/frag/assinaturas.js"),
						"assinaturas",
						false)));

		ROLE_SECTIONS.put(Cargo.ADMINISTRADOR, Arrays.asList(
				new Secao(
						"/images/icons/pessoa.svg",
						"/html/admin/sections/matriculas.html",
						"Gestão de matrículas",
						Arrays.asList("/css/admin/matriculas.css"),
						Arrays.asList("/js/admin/frag/matriculas.js"),
						"matriculas",
						false),
				new Secao(
						"/images/icons/turmas.svg",
						"/html/admin/sections/turmas.html",
						"Gestão de turmas",
						Arrays.asList("https://cdn.jsdelivr.net/npm/choices.js/public/assets/styles/choices.min.css",
											"/css/admin/turmas.css"),
						Arrays.asList("https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js",
											"/js/admin/frag/subturmas.js", "/js/admin/frag/turmas.js"),
						"turmas",
						false),
				new Secao(
						"/images/icons/add-documentos.svg",
						"/html/admin/sections/documentos.html",
						"Gestão de documentos",
						Arrays.asList("/css/admin/documentos.css"),
						Arrays.asList("/js/admin/frag/usuarios.js", "/js/admin/frag/documentos.js"),
						"documentos",
						false)));

		ROLE_SECTIONS.put(Cargo.BIBLIOTECARIO, Arrays.asList(
				new Secao(
						"/images/icons/livros.svg",
						"/html/bibliotecario/sections/livros.html",
						"Gestão de livros",
						Arrays.asList("/css/bibliotecario/livros.css"),
						Arrays.asList("/js/bibliotecario/frag/livros.js"),
						"livros",
						false),
				new Secao(
						"/images/icons/emprestimos.svg",
						"/html/bibliotecario/sections/emprestimos.html",
						"Gestão de empréstimos e reservas",
						Arrays.asList("/css/bibliotecario/emprestimos.css"),
						Arrays.asList("/js/bibliotecario/frag/emprestimos.js"),
						"emprestimos",
						false)));
	}

	public List<Secao> getSecoesPorCargo(Usuario usuario) {
		if (usuario == null || usuario.getCargo() == null)
			return new ArrayList<>(); // Retorne ArrayList em vez de List.of()

		return ROLE_SECTIONS.get(usuario.getCargo());
	}

	public Map<String, Object> getSecaoData(Usuario usuario, String idSecao)
			throws IllegalAccessException, IOException {
		Map<String, Object> data = new HashMap<>();

		if (usuario == null || usuario.getCargo() == null)
			return data;

		List<Secao> secoes = ROLE_SECTIONS.get(usuario.getCargo());
		if (secoes == null)
			return data;

		Secao secao = secoes.stream()
				.filter(s -> s.getSecao().equals(idSecao))
				.findFirst()
				.orElse(null);

		if (secao == null) {
			return data;
		}

		data.put("html", secao.getHtml());

		List<String> cssFiles = new ArrayList<>(secao.getCss());
		data.put("css", cssFiles);

		List<String> jsFiles = new ArrayList<>(secao.getJs());
		data.put("js", jsFiles);

		return data;
	}
}