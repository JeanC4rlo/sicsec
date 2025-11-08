// ---------- [ SEÇÃO: inicialização principal ] ----------
function initTurmas() {
  const secao = document.querySelector("#turmas");
  if (!secao) return;

  setupAbasTurmas(secao);
  setupFormularioTurma(secao);
  setupFormularioEditarTurma(secao);
}



// ---------- [ SEÇÃO: gerenciamento de abas internas ] ----------
function setupAbasTurmas(secao) {
  const botoes = secao.querySelectorAll("header button");
  const abas = secao.querySelectorAll(".tab");
  const ultimaAba = localStorage.getItem("turmasAbaAtiva") || "criar-turma";

  abas.forEach(aba => aba.classList.remove("ativo"));
  botoes.forEach(btn => btn.classList.remove("ativo"));

  botoes.forEach(btn => {
    btn.addEventListener("click", () => {
      const tab = btn.dataset.tab;
      trocarAba(abas, botoes, tab);
      localStorage.setItem("turmasAbaAtiva", tab);
    });
  });

  trocarAba(abas, botoes, ultimaAba);
}

function trocarAba(abas, botoes, tab) {
  abas.forEach(aba => aba.classList.toggle("ativo", aba.classList.contains(tab)));
  botoes.forEach(b => b.classList.toggle("ativo", b.dataset.tab === tab));
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


// ---------- [ SEÇÃO: formulário de cadastro de turmas ] ----------
function setupFormularioTurma(secao) {
  const cursoSelect = secao.querySelector("#cursoSelect");
  const disciplinaSelect = secao.querySelector("#disciplinaSelect");
  const professorSelect = secao.querySelector("#professorSelect");
  const alunoSelect = secao.querySelector("#alunoSelect");

  if (!cursoSelect || !disciplinaSelect || !professorSelect || !alunoSelect) return;

  const choicesProfessor = new Choices(professorSelect, {
    ...baseChoicesConfig,
    placeholderValue: "Selecione professores...",
    searchPlaceholderValue: "Buscar professor...",
    noResultsText: "Nenhum professor encontrado"
  });

  const choicesAluno = new Choices(alunoSelect, {
    ...baseChoicesConfig,
    placeholderValue: "Selecione alunos...",
    searchPlaceholderValue: "Buscar aluno...",
    noResultsText: "Nenhum aluno encontrado"
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
    if (!disciplinaId) return;
    carregarProfessores(disciplinaId, choicesProfessor);
  });
}



// ---------- [ SEÇÃO: formulário de edição de turmas ] ----------
function setupFormularioEditarTurma(secao) {
  const buscarForm = secao.querySelector("#buscarTurmaForm");
  const editarForm = secao.querySelector("#editarTurmaForm");
  const selectBuscar = buscarForm.querySelector("#nomeTurmaBuscar");

  // --- 1. Inicializa Choices.js no campo de busca ---
  const choicesBuscar = new Choices(selectBuscar, {
    ...baseChoicesConfig,
    searchPlaceholderValue: "Digite parte do nome da turma...",
    noResultsText: "Nenhuma turma encontrada",
    noChoicesText: "Carregando turmas...",
    itemSelectText: "Selecionar",
    removeItemButton: false,
    shouldSort: false,
    duplicateItemsAllowed: false,
  });

  // --- 2. Busca todas as turmas do curso (uma única requisição) ---
  async function carregarTurmasDoCurso() {
    try {
      // Obtém usuário atual para determinar o curso
      const usuarioResp = await fetch("/api/usuario/atual/admin", { method: "POST" });
      if (!usuarioResp.ok) throw new Error("Erro ao obter usuário");
      const usuario = await usuarioResp.json();

      let endpoint;
      switch (usuario.cargo) {
        case "ROOT":
          endpoint = "/api/turma/getAll";
          break;
        case "CHEFE_DE_DEPARTAMENTO":
          endpoint = `/api/turma/departamento/${usuario.departamento}`;
          break;
        case "COORDENADOR":
          endpoint = `/api/turma/curso/${usuario.curso}`;
          break;
        default:
          throw new Error("Cargo não autorizado");
      }

      const res = await fetch(endpoint, { method: "POST" });
      if (!res.ok) throw new Error("Erro ao carregar turmas");
      const turmas = await res.json();

      // Formata lista de opções
      const opcoes = turmas.map(t => ({
        value: t.id,
        label: `${t.nome} (${t.curso?.nome || "Sem curso"})`
      }));

      // Popula o Choices com todas as turmas
      choicesBuscar.clearChoices();
      choicesBuscar.setChoices(opcoes, "value", "label", true);
    } catch (err) {
      console.error("Falha ao carregar turmas:", err);
      choicesBuscar.clearChoices();
      choicesBuscar.setChoices([{ value: "", label: "Erro ao carregar turmas" }], "value", "label", true);
    }
  }

  carregarTurmasDoCurso();

  // --- 3. Ao enviar o formulário, carrega a turma selecionada ---
  buscarForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const turmaId = selectBuscar.value;
    if (!turmaId) {
      alert("Selecione uma turma para editar");
      return;
    }

    try {
      const res = await fetch(`/api/turma/${turmaId}`, { method: "POST" });
      if (!res.ok) throw new Error("Erro ao carregar turma");
      const turma = await res.json();

      editarForm.style.display = "block";
      editarForm.querySelector("#turmaId").value = turma.id;
      editarForm.querySelector('input[name="nome"]').value = turma.nome;

      const cursoSelect = editarForm.querySelector("#cursoEditarSelect");
      const disciplinaSelect = editarForm.querySelector("#disciplinaEditarSelect");
      const professorSelect = editarForm.querySelector("#professorEditarSelect");
      const alunoSelect = editarForm.querySelector("#alunoEditarSelect");

      // Choices para múltiplos professores e alunos
      const choicesProfessor = new Choices(professorSelect, {
        ...baseChoicesConfig,
        placeholderValue: "Selecione professores...",
        searchPlaceholderValue: "Buscar professor...",
        noResultsText: "Nenhum professor encontrado"
      });

      const choicesAluno = new Choices(alunoSelect, {
        ...baseChoicesConfig,
        placeholderValue: "Selecione alunos...",
        searchPlaceholderValue: "Buscar aluno...",
        noResultsText: "Nenhum aluno encontrado"
      });

      carregarCursos(cursoSelect);
      carregarDisciplinas(turma.curso.id, disciplinaSelect, choicesProfessor);
      carregarProfessores(turma.disciplina.id, choicesProfessor);
      carregarAlunos(turma.curso.id, choicesAluno);
    } catch (err) {
      console.error(err);
      alert("Falha ao carregar turma selecionada");
    }
  });
}




// ---------- [ SEÇÃO: funções de integração com servidor ] ----------
async function carregarCursos(select) {
  try {
    const usuarioResp = await fetch("/api/usuario/atual/admin", { method: 'POST' });
    if (!usuarioResp.ok) throw new Error("Falha ao obter usuário atual");
    const usuario = await usuarioResp.json();

    let endpoint;
    switch (usuario.cargo) {
      case "ROOT":
        endpoint = "/api/curso/getAll";
        break;
      case "CHEFE_DE_DEPARTAMENTO":
        endpoint = `/api/curso/departamento/${usuario.departamento}`;
        break;
      case "COORDENADOR":
        endpoint = `/api/curso/${usuario.curso}`;
        break;
      default:
        throw new Error("Cargo não autorizado");
    }

    const cursosResp = await fetch(endpoint, { method: 'POST' });
    if (!cursosResp.ok) throw new Error("Falha ao carregar cursos");
    const cursos = await cursosResp.json();

    select.innerHTML = "";

    if (usuario.cargo === "COORDENADOR") {
      const curso = Array.isArray(cursos) ? cursos[0] : cursos;
      select.innerHTML = `<option value="${curso.id}" selected>${curso.nome}</option>`;
      select.disabled = true;
      select.dispatchEvent(new Event("change"));
      return;
    }

    const placeholder =
      usuario.cargo === "ROOT"
        ? "Selecione um curso"
        : "Selecione um curso do seu departamento";
    select.innerHTML = `<option value="">${placeholder}</option>`;

    cursos.forEach(c => {
      select.innerHTML += `<option value="${c.id}">${c.nome}</option>`;
    });

    select.disabled = false;

  } catch (err) {
    console.error(err);
    preencherSelect(select, [], "Erro ao carregar cursos");
  }
}

function carregarDisciplinas(cursoId, select, choicesProfessor) {
  fetch(`/api/disciplina/curso/${cursoId}`, { method: 'POST' })
    .then(res => res.json())
    .then(disciplinas => {
      preencherSelect(select, disciplinas, "Selecione uma disciplina");
      if (disciplinas.length > 0)
        carregarProfessores(disciplinas[0].id, choicesProfessor);
    })
    .catch(() => preencherSelect(select, [], "Erro ao carregar disciplinas"));
}

function carregarProfessores(disciplinaId, choicesProfessor) {
  fetch(`/api/professor/disciplina/${disciplinaId}`, { method: 'POST' })
    .then(res => res.json())
    .then(professores => {
      const lista = professores.map(p => ({
        id: p.id,
        nome: `${p.matricula.numeroMatricula} - ${p.matricula.nome}`
      }));
      preencherSelect(choicesProfessor, lista);
    })
    .catch(() => preencherSelect(choicesProfessor, [], "Erro ao carregar professores"));
}

function carregarAlunos(cursoId, choicesAluno) {
  fetch(`/api/aluno/curso/${cursoId}`, { method: 'POST' })
    .then(res => res.json())
    .then(alunos => {
      const lista = alunos.map(a => ({
        id: a.id,
        nome: `${a.matricula.numeroMatricula} - ${a.matricula.nome}`
      }));
      preencherSelect(choicesAluno, lista);
    })
    .catch(() => preencherSelect(choicesAluno, [], "Erro ao carregar alunos"));
}



// ---------- [ SEÇÃO: utilitário genérico ] ----------
function preencherSelect(selectOuChoices, dados, placeholder = null) {
  const isChoices = typeof selectOuChoices.setChoices === "function";
  if (isChoices) {
    selectOuChoices.clearStore();
    const choices = dados.map(item => ({
      value: item.id,
      label: item.nome
    }));
    selectOuChoices.setChoices(choices, 'value', 'label', true);
    return;
  }

  const select = selectOuChoices;
  const options = [];
  if (placeholder) {
    const opt = document.createElement("option");
    opt.value = "";
    opt.textContent = placeholder;
    options.push(opt);
  }
  dados.forEach(item => {
    const opt = document.createElement("option");
    opt.value = item.id;
    opt.textContent = item.nome;
    options.push(opt);
  });
  select.replaceChildren(...options);
}

function toggleFormFields(form, disable) {
  form.querySelectorAll("input, select, textarea, button").forEach(el => {
    if (disable) el.setAttribute("disabled", "true");
    else el.removeAttribute("disabled");
  });
}