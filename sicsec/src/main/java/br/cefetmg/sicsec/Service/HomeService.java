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
						Arrays.asList("/css/home/home.css"),
						Arrays.asList("/js/aluno/frag/homepage.js"),
						"homepage",
						true),
				new Secao(
						"/images/icons/boletim.svg",
						"/html/aluno/sections/boletim.html",
						"Boletim",
						Arrays.asList("/css/home/home.css"),
						Arrays.asList("/js/aluno/frag/boletim.js"),
						"boletim",
						false),
				new Secao(
						"/images/icons/turmas.svg",
						"/html/aluno/sections/turmas.html",
						"Turmas",
						Arrays.asList("/css/home/home.css"),
						Arrays.asList("/js/aluno/frag/turmas.js"),
						"turmas",
						false),
				new Secao(
						"/images/icons/biblioteca.svg",
						"/html/aluno/sections/biblioteca.html",
						"Biblioteca",
						Arrays.asList("/css/home/home.css"),
						Arrays.asList(),
						"biblioteca",
						false),
				new Secao(
						"/images/icons/bolsas.svg",
						"/html/aluno/sections/bolsas.html",
						"Bolsas",
						Arrays.asList("/css/home/home.css"),
						Arrays.asList("/js/aluno/frag/bolsas.js"),
						"bolsas",
						false)));

		ROLE_SECTIONS.put(Cargo.PROFESSOR, Arrays.asList(
				new Secao(
						"/images/icons/materia.svg",
						"/html/professor/sections/atividades.html",
						"Atividades",
						Arrays.asList("/css/professor/atividades.css"),
						Arrays.asList("/js/professor/frag/atividades.js"),
						"atividades",
						false),
				new Secao(
						"/images/icons/turmas.svg",
						"/html/professor/sections/turmas.html",
						"Turmas",
						Arrays.asList("/css/professor/turmas.css"),
						Arrays.asList("/js/professor/home.js"),
						"turmas",
						false)));

		ROLE_SECTIONS.put(Cargo.ADMINISTRADOR, Arrays.asList(
				new Secao(
						"/images/icons/pessoa.svg",
						"/html/admin/sections/matriculas.html",
						"Gestão de matrículas",
						Arrays.asList("/css/admin/matriculas.css"),
						Arrays.asList("/js/admin/frag/initMatriculas.js"),
						"matriculas",
						false),
				new Secao(
						"/images/icons/turmas.svg",
						"/html/admin/sections/turmas.html",
						"Gestão de turmas",
						Arrays.asList("/css/admin/turmas.css"),
						Arrays.asList("/js/admin/frag/initTurmas.js"),
						"turmas",
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
		data.put("css", new ArrayList<>(secao.getCss()));
		data.put("js", new ArrayList<>(secao.getJs()));

		return data;
	}
}