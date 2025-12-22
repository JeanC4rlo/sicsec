let listaTurmas;
let materiaAtual;
let localAtual;
let horarioAtual;

async function initTurmas() {

    listaTurmas = document.querySelector(".lista-turmas");

    const turmas = await fetchJSON("/api/aluno/acesso/turma/listar");

    turmas.forEach(turma => {
        const botao = document.createElement("button");
        botao.textContent = turma.nome;
        botao.classList.add("botao-turma");
        botao.addEventListener("click", () => selecionarTurma(turma.id, botao));
        if(turma.tipo == "SUBTURMA") botao.classList += " sub-turma"
        listaTurmas.appendChild(botao);
    });

    selecionarTurma(turmas[0].id, listaTurmas.querySelector(".botao-turma"));

}

async function fetchJSON(url, options = { method: "POST" }) {
  const res = await fetch(url, options);
  if (!res.ok) throw new Error(`Erro HTTP ${res.status} em ${url}`);
  return res.json();
}

initTurmas();
initTurmasTabs();