let listaTurmas;
let materiaAtual;
let localAtual;
let horarioAtual;

async function initTurmas() {
    listaTurmas = document.querySelector("#turmas .lista-turmas");
    materiaAtual = document.querySelector(".value.materia");
    localAtual = document.querySelector(".value.local");
    horarioAtual = document.querySelector(".value.horario");

    const turmas = await fetchJSON("/api/aluno/acesso/turma/listar");

    turmas.forEach(turma => {
        const botao = document.createElement("button");
        botao.textContent = turma.nome;
        botao.classList.add("botao-turma");
        botao.addEventListener("click", () => selecionarTurma(turma.id, botao));
        listaTurmas.appendChild(botao);
    });

    const turmaSalva = sessionStorage.getItem("turmaSelecionada");

}