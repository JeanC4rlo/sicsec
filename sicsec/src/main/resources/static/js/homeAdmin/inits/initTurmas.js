// ---------- [ SEÇÃO: inicialização principal ] ----------
function initTurmas() {
  console.log("Inicializando gestão de turmas...");

  const secao = document.querySelector("#turmas");
  if (!secao) return;

  setupAbasTurmas(secao);
  setupFormularioTurma(secao);
}



// ---------- [ SEÇÃO: gerenciamento de abas internas ] ----------
function setupAbasTurmas(secao) {
  const botoes = secao.querySelectorAll("header button");
  const abas = secao.querySelectorAll(".tab");

  const ultimaAba = localStorage.getItem("turmasAbaAtiva") || "criar-turma";

  // Remove estados antigos
  abas.forEach(aba => aba.classList.remove("ativo"));
  botoes.forEach(btn => btn.classList.remove("ativo"));

  // Configura alternância
  botoes.forEach(btn => {
    btn.addEventListener("click", () => {
      const tab = btn.dataset.tab;
      trocarAba(abas, botoes, tab);
      localStorage.setItem("turmasAbaAtiva", tab);
    });
  });

  // Ativa a aba salva ou padrão
  trocarAba(abas, botoes, ultimaAba);
}

function trocarAba(abas, botoes, tab) {
  abas.forEach(aba => aba.classList.toggle("ativo", aba.classList.contains(tab)));
  botoes.forEach(b => b.classList.toggle("ativo", b.dataset.tab === tab));
}



// ---------- [ SEÇÃO: formulário de cadastro de turmas ] ----------
function setupFormularioTurma(secao) {
  const cursoSelect = secao.querySelector("#cursoSelect");
  const disciplinaSelect = secao.querySelector("#disciplinaSelect");
  const professorSelect = secao.querySelector("#professorSelect");
  const alunoSelect = secao.querySelector("#alunoSelect");

  if (!cursoSelect || !disciplinaSelect || !professorSelect || !alunoSelect) return;

  carregarCursos(cursoSelect);

  cursoSelect.addEventListener("change", () => {
    const cursoId = cursoSelect.value;
    if (!cursoId) return;
    carregarDisciplinas(cursoId, disciplinaSelect); 
    carregarAlunos(cursoId, alunoSelect);
    });

  disciplinaSelect.addEventListener("change", () => {
    const disciplinaId = disciplinaSelect.value;
    if (!disciplinaId) return;
    carregarProfessores(disciplinaId, professorSelect);
  });
}



// ---------- [ SEÇÃO: funções de integração com servidor ] ----------
async function carregarCursos(select) {
  try {
    // --- 1. obtém dados do usuário logado ---
    const usuarioResp = await fetch("/api/usuario/atual/admin", {method: 'POST'});
    if (!usuarioResp.ok) throw new Error("Falha ao obter usuário atual");
    const usuario = await usuarioResp.json();

    // --- 2. define endpoint e comportamento conforme cargo ---
    let endpoint;
    switch (usuario.cargo) {
      case "ROOT":
        endpoint = "/api/curso/getAll";
        break;

      case "CHEFE_DE_DEPARTAMENTO":
        endpoint = `/api/curso/departamento/${usuario.departamentoId}`;
        break;

      case "COORDENADOR":
        endpoint = `/api/curso/${usuario.cursoId}`;
        break;

      default:
        throw new Error("Cargo não autorizado para acessar cursos");
    }

    // --- 3. busca cursos disponíveis ---
    const cursosResp = await fetch(endpoint, {method: 'POST'});
    if (!cursosResp.ok) throw new Error("Falha ao carregar cursos");
    const cursos = await cursosResp.json();

    // --- 4. popula o select conforme cargo ---
    select.innerHTML = "";

    if (usuario.cargo === "COORDENADOR") {
      // Coordenador → curso único, já selecionado e bloqueado
      const curso = Array.isArray(cursos) ? cursos[0] : cursos;
      select.innerHTML = `<option value="${curso.id}" selected>${curso.nome}</option>`;
      select.disabled = true;
      // dispara evento de change para carregar disciplinas automaticamente
      select.dispatchEvent(new Event("change"));
      return;
    }

    // ROOT e CHEFE_DE_DEPARTAMENTO → lista de cursos disponíveis
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

function carregarDisciplinas(cursoId, select) {
  fetch(`/api/disciplinas/curso/${cursoId}`, {method: 'POST'})
    .then(res => res.json())
    .then(disciplinas => {
      preencherSelect(select, disciplinas, "Selecione uma disciplina");
    })
    .catch(() => preencherSelect(select, [], "Erro ao carregar disciplinas"));
}

function carregarProfessores(disciplinaId, select) {
  fetch(`/api/professores/disciplina/${disciplinaId}`, {method: 'POST'})
    .then(res => res.json())
    .then(professores => {
      preencherSelect(select, professores, "Selecione um professor");
    })
    .catch(() => preencherSelect(select, [], "Erro ao carregar professores"));
}

function carregarAlunos(cursoId, select) {
  fetch(`/api/alunos/curso/${cursoId}`)
    .then(res => res.json())
    .then(alunos => {
      preencherSelect(select, alunos, "Selecione um aluno");
    })
    .catch(() => preencherSelect(select, [], "Erro ao carregar alunos"));
}



// ---------- [ SEÇÃO: utilitário genérico ] ----------
function preencherSelect(select, dados, placeholder) {
  select.innerHTML = `<option value="">${placeholder}</option>`;
  dados.forEach(item => {
    select.innerHTML += `<option value="${item.id}">${item.nome}</option>`;
  });
}
