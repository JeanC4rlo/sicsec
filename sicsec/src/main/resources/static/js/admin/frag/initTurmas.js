(function initTurmas() {
	const secao = document.querySelector(".turmas");
	if (!secao) return;

	setupAbasTurmas(secao);
	setupFormularioTurma(secao);
	setupFormularioEditarTurma(secao);

	function setupAbasTurmas(secao) {
		const botoes = secao.querySelectorAll("header button");
		const abas = secao.querySelectorAll(".tab");
		const ultimaAba = localStorage.getItem("turmasAbaAtiva") || "criar-turma";

		const trocarAba = (tab) => {
			abas.forEach(aba => aba.classList.toggle("ativo", aba.classList.contains(tab)));
			botoes.forEach(btn => btn.classList.toggle("ativo", btn.dataset.tab === tab));
			localStorage.setItem("turmasAbaAtiva", tab);
		};

		botoes.forEach(btn => btn.addEventListener("click", () => trocarAba(btn.dataset.tab)));
		trocarAba(ultimaAba);
	}

	const baseChoicesConfig = {
		searchEnabled: true,
		noResultsText: "Nenhum resultado encontrado",
		noChoicesText: "Carregando opções...",
		itemSelectText: "Selecionar",
		removeItemButton: true,
		shouldSort: false,
		duplicateItemsAllowed: false,
		addItems: true,
		addChoices: true,
		addItemFilter: () => false,
		paste: false,
		editItems: false,
		allowHTML: false
	};

	function setupFormularioTurma(secao) {
		const cursoSelect = secao.querySelector("#cursoSelect");
		const disciplinaSelect = secao.querySelector("#disciplinaSelect");
		const professorSelect = secao.querySelector("#professorSelect");
		const alunoSelect = secao.querySelector("#alunoSelect");

		if (!cursoSelect || !disciplinaSelect || !professorSelect || !alunoSelect) return;

		const choicesProfessor = new Choices(professorSelect, {
			...baseChoicesConfig,
			placeholderValue: "Selecione professores...",
			searchPlaceholderValue: "Buscar professor..."
		});

		const choicesAluno = new Choices(alunoSelect, {
			...baseChoicesConfig,
			placeholderValue: "Selecione alunos...",
			searchPlaceholderValue: "Buscar aluno..."
		});

		carregarCursos(cursoSelect);

		cursoSelect.addEventListener("change", () => {
			const cursoId = cursoSelect.value;
			if (!cursoId) return;
			carregarDisciplinas(cursoId, disciplinaSelect, choicesProfessor);
			carregarAlunos(cursoId, choicesAluno);
		});

		disciplinaSelect.addEventListener("change", () => {
			const disciplinaId = disciplinaSelect.value;
			if (disciplinaId) carregarProfessores(disciplinaId, choicesProfessor);
		});
	}

	function setupFormularioEditarTurma(secao) {
		const buscarForm = secao.querySelector("#buscarTurmaForm");
		const editarForm = secao.querySelector("#editarTurmaForm");
		const selectBuscar = buscarForm.querySelector("#nomeTurmaBuscar");

		const choicesBuscar = new Choices(selectBuscar, {
			...baseChoicesConfig,
			searchPlaceholderValue: "Digite parte do nome da turma...",
			removeItemButton: false
		});

		carregarTurmasDoUsuario(choicesBuscar);

		buscarForm.addEventListener("submit", async (e) => {
			e.preventDefault();
			const turmaId = selectBuscar.value;
			if (!turmaId) return alert("Selecione uma turma para editar");

			try {
				const turma = await fetchJSON(`/api/turma/${turmaId}`);
				preencherFormularioEdicao(editarForm, turma);
			} catch (err) {
				console.error(err);
				alert("Falha ao carregar turma selecionada");
			}
		});
	}

	async function carregarCursos(select) {
		try {
			const usuario = await fetchJSON("/api/usuarios/atual/admin");
			const endpoint = {
				ROOT: "/api/curso/getAll",
				CHEFE_DE_DEPARTAMENTO: `/api/curso/departamento/${usuario.departamento}`,
				COORDENADOR: `/api/curso/${usuario.curso}`
			}[usuario.cargo];

			if (!endpoint) throw new Error("Cargo não autorizado");

			const cursos = await fetchJSON(endpoint);
			preencherSelectCursos(select, cursos, usuario);
		} catch (err) {
			console.error(err);
			preencherSelect(select, [], "Erro ao carregar cursos");
		}
	}

	function carregarDisciplinas(cursoId, select, choicesProfessor) {
		fetchJSON(`/api/disciplina/curso/${cursoId}`)
			.then(disciplinas => {
				preencherSelect(select, disciplinas, "Selecione uma disciplina");
				choicesProfessor.clearStore();
			})
			.catch(() => preencherSelect(select, [], "Erro ao carregar disciplinas"));
	}

	function carregarProfessores(disciplinaId, choices) {
		return fetchJSON(`/api/professor/disciplina/${disciplinaId}`)
			.then(profs => preencherSelect(choices, profs.map(p => ({ id: p.id, nome: `${p.numeroMatricula} - ${p.nome}` }))))
			.catch(() => preencherSelect(choices, [], "Erro ao carregar professores"));
	}

	function carregarAlunos(cursoId, choices) {
		return fetchJSON(`/api/aluno/curso/${cursoId}`)
			.then(alunos => preencherSelect(choices, alunos.map(a => ({ id: a.id, nome: `${a.numeroMatricula} - ${a.nome}` }))))
			.catch(() => preencherSelect(choices, [], "Erro ao carregar alunos"));
	}

	async function carregarTurmasDoUsuario(choicesBuscar) {
		try {
			const usuario = await fetchJSON("/api/usuarios/atual/admin");
			const endpoint = {
				ROOT: "/api/turma/getAll",
				CHEFE_DE_DEPARTAMENTO: `/api/turma/departamento/${usuario.departamento}`,
				COORDENADOR: `/api/turma/curso/${usuario.curso}`
			}[usuario.cargo];

			const turmas = await fetchJSON(endpoint);
			const opcoes = turmas.map(t => ({ value: t.id, label: `${t.nome} (${t.curso.nome || "Sem curso"})` }));
			choicesBuscar.clearChoices();
			choicesBuscar.setChoices(opcoes, "value", "label", true);
		} catch (err) {
			console.error("Falha ao carregar turmas:", err);
			choicesBuscar.clearChoices();
			choicesBuscar.setChoices([{ value: "", label: "Erro ao carregar turmas" }], "value", "label", true);
		}
	}

	async function fetchJSON(url, options = { method: "POST" }) {
		const res = await fetch(url, options);
		if (!res.ok) throw new Error(`Erro HTTP ${res.status} em ${url}`);
		return res.json();
	}

	function preencherSelect(selectOuChoices, dados, placeholder = null) {
		const isChoices = typeof selectOuChoices.setChoices === "function";

		if (isChoices) {
			selectOuChoices.clearStore();
			const choices = dados.map(item => ({ value: item.id, label: item.nome }));
			selectOuChoices.setChoices(choices, "value", "label", true);
		} else {
			const select = selectOuChoices;
			select.innerHTML = "";
			if (placeholder) select.innerHTML = `<option value="">${placeholder}</option>`;
			dados.forEach(d => {
				const opt = document.createElement("option");
				opt.value = d.id;
				opt.textContent = d.nome;
				select.append(opt);
			});
		}
	}

	function preencherSelectCursos(select, cursos, usuario) {
		if (usuario.cargo === "COORDENADOR") {
			const curso = Array.isArray(cursos) ? cursos[0] : cursos;
			select.innerHTML = `<option value="${curso.id}" selected>${curso.nome}</option>`;
			select.disabled = true;
			select.dispatchEvent(new Event("change"));
		} else {
			select.innerHTML = `<option value="">Selecione um curso</option>`;
			cursos.forEach(c => {
				select.innerHTML += `<option value="${c.id}">${c.nome}</option>`;
			});
			select.disabled = false;
		}
	}

	function preencherFormularioEdicao(form, turma) {
		form.querySelector("#turmaId").value = turma.id;
		form.style.display = "block";
		form.querySelector('input[name="nome"]').value = turma.nome;

		const cursoSel = form.querySelector("#cursoEditarSelect");
		const discSel = form.querySelector("#disciplinaEditarSelect");

		cursoSel.innerHTML = `<option value="${turma.curso.id}" selected>${turma.curso.nome}</option>`;
		discSel.innerHTML = `<option value="${turma.disciplina.id}" selected>${turma.disciplina.nome}</option>`;
		cursoSel.disabled = discSel.disabled = true;

		const profChoices = new Choices(form.querySelector("#professorEditarSelect"), {
			...baseChoicesConfig,
			placeholderValue: "Selecione professores...",
			searchPlaceholderValue: "Buscar professor..."
		});
		const alunoChoices = new Choices(form.querySelector("#alunoEditarSelect"), {
			...baseChoicesConfig,
			placeholderValue: "Selecione alunos...",
			searchPlaceholderValue: "Buscar aluno..."
		});

		Promise.all([
			carregarProfessores(turma.disciplina.id, profChoices),
			carregarAlunos(turma.curso.id, alunoChoices)
		]).then(() => {
			turma.professores?.forEach(p => profChoices.setChoiceByValue(String(p.id)));
			turma.alunos?.forEach(a => alunoChoices.setChoiceByValue(String(a.id)));
			console.log("Alunos:", turma.alunos);
			console.log("Professores:", turma.professores);
		});

		form.querySelector("#arquivarTurma").checked = !turma.ativo;
	}
})();