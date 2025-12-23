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

const subTurmaTemplate =
{
    id: null,
    nomeEl: null,
    pChoices: null,
    aChoices: null
};

const subTurmas = []

function esconderSubturmaForms(secao, quantInput) {

    quantInput.parentElement.parentElement.style.display = "none";

    secao.innerHTML = "";
    secao.hidden = true;

    subTurmas.length = 0;

}

function mostrarSubturmaForms(secao, quantInput) {

    secao.hidden = false;
    quantInput.parentElement.parentElement.style.display = "flex";
    const quant = Number(quantInput.value);

    for(let i = 0; i<quant; i++) adicionarSubturmaForms(secao, i);

    const nomeTurma = secao.parentElement.querySelector(".criar-turma #nome-turma");

    nomeTurma.addEventListener("change", () => atualizarNomesSubturmas(nomeTurma.value));

    const professorSelect = secao.parentElement.querySelector("#professorSelect");
    const alunoSelect = secao.parentElement.querySelector("#alunoSelect");

    professorSelect.addEventListener("change", () => atualizarProfessoresSubturmas());
    alunoSelect.addEventListener("change", () => atualizarAlunosSubturmas());

}

function adicionarSubturmaForms(secao, i) {

    const nomeTurma = document.querySelector("form #nome-turma").value;

    const subSecao = document.createElement("div");

    let html = `
        <h2> ${(nomeTurma == "") ? ("T" + (i+1)) : (nomeTurma + " T" + (i+1))} </h2>

        <div class="subturma-professor-section">
            <label>Professor(es):</label>
            <select id="professorSelect" class="subturma" name="professores-subturma${i}" multiple>
            </select>
        </div>

        <div class="subturma-aluno-section">
            <label>Aluno(s):</label>
            <select id="alunoSelect" class="subturma" name="alunos-subturma${i}" multiple>
            </select>
        </div>
        `;

    subSecao.innerHTML = html;

    const nomeEl = subSecao.querySelector("h2");
    const professorSelect = subSecao.querySelector("#professorSelect");
    const alunoSelect = subSecao.querySelector("#alunoSelect");

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

    secao.appendChild(subSecao);

    const subTurma = {
        id: i,
        nomeEl: nomeEl,
        pChoices: choicesProfessor,
        aChoices: choicesAluno
    }
    subTurmas.push(subTurma);

    atualizarProfessoresSubturmas();
    atualizarAlunosSubturmas();

}

function removerSubturmaForms(secao) {

    subTurmas.pop();
    secao.removeChild(secao.lastElementChild);

}

function atualizarSubturmaForms(secao, quantInput) {

    const quant = Number(quantInput.value);

    if (quant < subTurmas.length) {
        removerSubturmaForms(secao, quant-1);
        return atualizarSubturmaForms(secao, quantInput);
    }

    if (quant > subTurmas.length) {
        adicionarSubturmaForms(secao, quant-1);
        return atualizarSubturmaForms(secao, quantInput);
    }

    return;

}

function atualizarNomesSubturmas(nome) {

    let i = 0;
    subTurmas.forEach(subTurma => {
        subTurma.nomeEl.innerHTML = (nome == "") ? ("T" + (i+1)) : (nome + " T" + (i+1));
        i++;
    });

}

function atualizarProfessoresSubturmas() {

    const selectEl = document.querySelector("#professorSelect:not(.subturma)");
    const selecao = selectEl._choicesInstance.getValue(true);

    subTurmas.forEach(subTurma => {
        let choices = subTurma.pChoices;
        let selecao_ = choices.getValue(true);

        choices.removeActiveItems();
        choices.clearChoices();

        let c = professoresArray.filter(
            p => selecao.includes(p.id)
        ).map(
            (p => ({ 
                value: p.id, 
                label: `${p.numeroMatricula} - ${p.nome}`, 
                selected: selecao_.includes(p)
        })));

        choices.setChoices(
            c, 
            "value",
            "label",
            false
        );

        selecao_.filter(s => selecao.includes(s))
                .forEach(s => choices.setChoiceByValue(s));

    });

}

function atualizarAlunosSubturmas() {

    const selectEl = document.querySelector("#alunoSelect:not(.subturma)");
    const selecao = selectEl._choicesInstance.getValue(true);

    subTurmas.forEach(subTurma => {
        let choices = subTurma.aChoices;
        let selecao_ = choices.getValue(true);

        choices.removeActiveItems();
        choices.clearChoices();

        let c = alunosArray.filter(
            a => selecao.includes(a.id)
        ).map(
            (a => ({ 
                value: a.id, 
                label: `${a.numeroMatricula} - ${a.nome}`, 
                selected: selecao_.includes(a)
        })));

        choices.setChoices(
            c, 
            "value",
            "label",
            false
        );

        selecao_.filter(s => selecao.includes(s))
                .forEach(s => choices.setChoiceByValue(s));

    });
}

function initSubTurmas(secao) {

    const permitirSubturmas = secao.querySelector("#toggleSubturmas");
    const quantSubturmas = secao.querySelector("#quantSubturmas");
    const secaoSubturmas = secao.querySelector(".subturmas-section");

    permitirSubturmas.addEventListener("change", () => {

        if (permitirSubturmas.checked)
            return mostrarSubturmaForms(secaoSubturmas, quantSubturmas);
        
        return esconderSubturmaForms(secaoSubturmas, quantSubturmas)
    });

    quantSubturmas.addEventListener("change", 
        () => {
            
            if(quantSubturmas.value > 4) quantSubturmas.value = 4;
            if(quantSubturmas.value < 1) quantSubturmas.value = 1;
            atualizarSubturmaForms(secaoSubturmas, quantSubturmas)

        }
    );

}