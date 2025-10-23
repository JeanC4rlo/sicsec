let btnSair;
let form;
let btnAnterior;
let btnProximo;
let idxSegmento;
let btnAddQuestao;
const MAX_NUM_CARACTERES = 1500;
let sessao;
let listaSegmentos;
let numQuestoes = 0;
const MAX_NUM_ALTERNATIVAS = 6;

window.onload = initProdAtividade;

function ativarForm() {
    sessao.classList.remove("ativo");
    form.classList.remove("inativo");
}

function definirSessao() {
    sessao = document.querySelector(`#section-${form.tipo.value}`)
    sessao.classList.add("ativo");
    listaSegmentos = sessao.querySelectorAll(":scope > div");
    form.classList.add("inativo");
}

function validarCampo(campo) {
    if (!campo.value.trim()) {
        campo.classList.add("campo-obrigatorio");
        setTimeout(() => campo.classList.remove("campo-obrigatorio"), 2000);
        campo.focus();
        return false;
    }
    return true;
}

function validarLista(container, config) {
    const itens = Array.from(container.children);

    for (const item of itens) {
        for (const sel of config.selectors) {
            const campos = container.querySelectorAll(`:scope > ${sel}`);
            for (const campo of campos) {
                if (!campo.value.trim()) {
                    validarCampo(campo);
                    return false;
                }
            }
        }
    }

    return true;
}

function validarContainer(container) {
    const campos = container.querySelectorAll('input, textarea, select');

    for (const campo of campos) {
        if (!campo.value.trim()) {
            validarCampo(campo);
            return false;
        }
    }

    return true;
}

function validarTempo() {
    const listaInputs = document.querySelectorAll("#duracao-e-tentativas > input");
    for (let input of listaInputs) {
        if (!input.value.trim()) {
            validarCampo(input);
            return false;
        }
    }
    return true;
}

function passar() {
    idxSegmento++;
    if (idxSegmento == 0) {
        definirSessao();
        listaSegmentos[idxSegmento].classList.add("ativo");
        return;
    }
    listaSegmentos[idxSegmento - 1].classList.remove("ativo");
    listaSegmentos[idxSegmento].classList.add("ativo");
}

function passarSegmento() {
    if (idxSegmento == -1) {
        for (let campo of form.elements) {
            if (!validarCampo(campo))
                return;
        }
        passar();
        return;
    }

    if (idxSegmento >= listaSegmentos.length - 1)
        return;

    switch (form.tipo.value) {
        case "questionario":
            switch (idxSegmento) {
                case 0:
                    if (!validarContainer(document.getElementById("lista-questoes")))
                        return;
                    passar();
                    break;
                case 1:
                    if (!validarLista(document.getElementById("duracao-e-tentativas"), { selectors: ["input"] }))
                        return;
                    passar();
                    break;
            }
            break;
    }
}

function voltar() {
    idxSegmento--;
    if (idxSegmento == -1) {
        listaSegmentos[idxSegmento + 1].classList.remove("ativo");
        ativarForm();
        return
    }
    listaSegmentos[idxSegmento + 1].classList.remove("ativo");
    listaSegmentos[idxSegmento].classList.add("ativo");
}

function voltarSegmento() {
    if (idxSegmento < 0)
        return;

    if (idxSegmento > 0) {
        listaSegmentos[idxSegmento].classList.remove("ativo");
        listaSegmentos[idxSegmento - 1].classList.add("ativo");
        idxSegmento--;
    } else {
        listaSegmentos[idxSegmento].classList.remove("ativo");
        ativarForm();
        idxSegmento = -1;
    }
}

function confirmarSaidaDaPag() {
    if (confirm("Tem certeza que deseja sair da página? Os dados da atividade serão perdidos?")) {
        window.location.href = "../homeProfessor.html";
    }
}

function adicionarQuestao() {
    numQuestoes++;

    const CORPO = document.createElement("div");
    CORPO.classList.add("questao");
    CORPO.classList.add("ativo");

    const NUM_QUESTAO = document.createElement("p");
    NUM_QUESTAO.innerHTML = `Questão ${numQuestoes}`;
    NUM_QUESTAO.classList.add("num-questao");

    const TXT_DA_QUESTAO = document.createElement("textarea");
    TXT_DA_QUESTAO.placeholder = "Texto da questão";
    TXT_DA_QUESTAO.maxLength = String(MAX_NUM_CARACTERES);
    expandirTextarea(TXT_DA_QUESTAO);

    const ENUNCIADO_QUESTAO = document.createElement("textarea");
    ENUNCIADO_QUESTAO.placeholder = "Enunciado da questão";
    ENUNCIADO_QUESTAO.style.marginBottom = "0.5rem";
    expandirTextarea(ENUNCIADO_QUESTAO);

    const LISTA_ALTERNATIVAS = document.createElement("div");
    LISTA_ALTERNATIVAS.id = "lista-alternativas";

    const BTN_ADD_ALTERNATIVA = document.createElement("button");
    BTN_ADD_ALTERNATIVA.textContent = "Adicionar alternativa";
    BTN_ADD_ALTERNATIVA.addEventListener("click", () => adicionarAlternativa(LISTA_ALTERNATIVAS));

    const BTN_RMV_QUESTAO = document.createElement("button");
    BTN_RMV_QUESTAO.textContent = "Excluir questão";
    BTN_RMV_QUESTAO.addEventListener("click", () => removerQuestao(CORPO));

    while (LISTA_ALTERNATIVAS.children.length < 2)
        adicionarAlternativa(LISTA_ALTERNATIVAS);

    CORPO.append(NUM_QUESTAO, TXT_DA_QUESTAO, ENUNCIADO_QUESTAO, LISTA_ALTERNATIVAS, BTN_ADD_ALTERNATIVA, BTN_RMV_QUESTAO);
    document.querySelector("#lista-questoes").appendChild(CORPO, LISTA_ALTERNATIVAS);
}

function removerQuestao(corpo) {
    if (numQuestoes < 3) {
        alert("Um questionário deve ter pelo menos 2 questões");
        return;
    }
    corpo.remove();
    const LISTA_NUM_QUESTOES = document.querySelectorAll("#lista-questoes .num-questao");
    LISTA_NUM_QUESTOES.forEach((num, index) => {
        num.innerHTML = `Questão ${index + 1}`;
    });

    numQuestoes--;
}

function adicionarAlternativa(listaAlternativas) {
    let numAlternativas = listaAlternativas.children.length;
    if (numAlternativas == MAX_NUM_ALTERNATIVAS) {
        alert("Uma questão pode ter no máximo 6 alternativas");
        return;
    }

    const CORPO = document.createElement("div");
    CORPO.classList.add("alternativa");

    const TEXTO = document.createElement("textarea");
    TEXTO.placeholder = "Texto da alternativa";
    expandirTextarea(TEXTO);

    const EH_CORRETA = document.createElement("select");
    EH_CORRETA.add(new Option("Errada", "false"));
    EH_CORRETA.add(new Option("Correta", "true"));

    const BTN_RMV_ALTERNATIVA = document.createElement("button");
    BTN_RMV_ALTERNATIVA.textContent = "Remover alternativa";
    BTN_RMV_ALTERNATIVA.addEventListener('click', () => removerAlternativa(CORPO))

    CORPO.append(TEXTO, EH_CORRETA);
    listaAlternativas.appendChild(CORPO);
}

function expandirTextarea(textarea) {
    textarea.addEventListener("input", function () {
        textarea.style.height = "auto";
        textarea.style.height = textarea.scrollHeight + "px";
        textarea.maxLength = String(MAX_NUM_CARACTERES);
    })
}

function initProdAtividade() {
    idxSegmento = -1;
    btnSair = document.getElementById("btn-sair");
    form = document.querySelector("form");
    btnAnterior = document.getElementById("btn-anterior");
    btnProximo = document.getElementById("btn-proximo");
    btnAddQuestao = document.getElementById("add-questao");

    btnSair.addEventListener("click", confirmarSaidaDaPag);
    btnProximo.addEventListener("click", passarSegmento);
    btnAnterior.addEventListener("click", voltarSegmento);
    btnAddQuestao.addEventListener("click", adicionarQuestao);
}