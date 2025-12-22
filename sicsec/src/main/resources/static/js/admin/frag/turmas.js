let professoresArray = [];
let alunosArray = [];
let choices = {
	professor: null,
	aluno: null
};

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

function setupFormularioTurma(secao) {
	const cursoSelect = secao.querySelector("#cursoSelect");
	const disciplinaSelect = secao.querySelector("#disciplinaSelect");
	const professorSelect = secao.querySelector("#professorSelect");
	const alunoSelect = secao.querySelector("#alunoSelect");

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

	professorSelect._choicesInstance = choicesProfessor;
	alunoSelect._choicesInstance = choicesAluno;

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
		searchPlaceholderValue: "Buscar turma...",
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

async function carregarProfessores(disciplinaId, choices) {

	professoresArray = await fetchJSON(`/api/professor/disciplina/${disciplinaId}`)
	.catch(() => preencherSelect(choices, [], "Erro ao carregar professores"));

	preencherSelect(choices, professoresArray.map(p => ({ id: p.id, nome: `${p.numeroMatricula} - ${p.nome}` })));

	return professoresArray;

}

async function carregarAlunos(cursoId, choices) {

	alunosArray = await fetchJSON(`/api/aluno/curso/${cursoId}`)
		.catch(() => preencherSelect(choices, [], "Erro ao carregar alunos"));
	
	preencherSelect(choices, alunosArray.map(a => ({ id: a.id, nome: `${a.numeroMatricula} - ${a.nome}` })))

	return alunosArray

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

function preencherSelect(select, dados, placeholder = null) {

	const isChoices = typeof select.setChoices === "function";

	if (isChoices) {
		select.clearStore();
		const choices = dados.map(item => ({ value: item.id, label: item.nome }));
		select.setChoices(choices, "value", "label", true);
		return;
	}
	
	select.innerHTML = "";

	if (placeholder) select.innerHTML = `<option value="">${placeholder}</option>`;
	dados.forEach(d => {
		const opt = document.createElement("option");
		opt.value = d.id;
		opt.textContent = d.nome;
		select.append(opt);
	});

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

function initTurmas() {
	const secao = document.querySelector("#turmas-content");
	if (!secao) return;

	setupAbasTurmas(secao);
	setupFormularioTurma(secao);
	setupFormularioEditarTurma(secao);
	initSubTurmas(secao);

}

initTurmas();