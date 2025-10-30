let btnSair;
let form;
let btnAnterior;
let btnProximo;
let idxSegmento;
let btnAddQuestao;
let sessao;
let listaSegmentos;
let numQuestoes = 0;
let btnEnviar;
const MAX_NUM_ALTERNATIVAS = 6;
const MAX_CARACTERES_TXT_PEQUENO = 252;
const MAX_CARACTERES_TXT_GRANDE = 1500;
const tipos = {
    "questionario": "Questionário",
    "envArquivo": "Envio de Arquivo",
    "envTexto": "Envio de Texto"
}

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
        destacarCampo(campo);
        return false;
    }
    return true;
}

function destacarCampo(campo) {
    campo.classList.add("campo-obrigatorio");
    setTimeout(() => campo.classList.remove("campo-obrigatorio"), 2000);
    campo.focus();
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

function validarData(data) {
    return (new Date(data).getTime() > new Date().getTime());
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
        if (!validarData(form.data.value)) {
            alert("A data inserida é inválida")
            destacarCampo(form.data);
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
                    if (numQuestoes < 2) {
                        alert("Um questionário deve ter pelo menos 2 questões");
                        return;
                    }
                    if (!validarContainer(sessao.querySelector("#lista-questoes")))
                        return;
                    passar();
                    break;
                case 1:
                    if (!validarLista(sessao.querySelector("#duracao-e-tentativas"), { selectors: ["input"] }))
                        return;
                    passar();
                    montarConfirmacao();
                    break;
            }
            break;
        case "envTexto":
            switch (idxSegmento) {
                case 0:
                    if (!validarContainer(sessao.querySelector(".elaboracao")))
                        return;
                    passar();
                    montarConfirmacao();
                    break;
            }
            break;
        case "envArquivo":
            switch (idxSegmento) {
                case 0:
                    if (!validarContainer(sessao.querySelector(".elaboracao")))
                        return;
                    passar();
                    montarConfirmacao();
                    break;
            }
            break;
    }
}

function aparecerBotaoEnviar() {
    btnEnviar.classList.remove("inativo");
    
}

/*function desaparecerBotaoEnviar() {
    btnEnviar.classList.add("inativo");
    btnProximo.classList.remove("inativo");
}*/

function voltar() {
    idxSegmento--;
    if (idxSegmento == -1) {
        listaSegmentos[idxSegmento + 1].classList.remove("ativo");
        ativarForm();
        return
    }
    listaSegmentos[idxSegmento + 1].classList.remove("ativo");
    listaSegmentos[idxSegmento].classList.add("ativo");
    desaparecerBotaoEnviar();
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

    const corpo = document.createElement("div");
    corpo.classList.add("questao");
    corpo.classList.add("ativo");

    const numDaQuestao = document.createElement("p");
    numDaQuestao.innerHTML = `Questão ${numQuestoes}`;
    numDaQuestao.classList.add("num-questao");

    const txtDaQuestao = document.createElement("textarea");
    txtDaQuestao.placeholder = "Texto da questão";
    txtDaQuestao.maxLength = String(MAX_CARACTERES_TXT_GRANDE);
    expandirTextarea(txtDaQuestao);

    const enunciadoQuestao = document.createElement("textarea");
    enunciadoQuestao.placeholder = "Enunciado da questão";
    enunciadoQuestao.style.marginBottom = "0.5rem";
    expandirTextarea(enunciadoQuestao);

    const listaAlternativas = document.createElement("div");
    listaAlternativas.id = "lista-alternativas";

    const btnAddAlternativa = document.createElement("button");
    btnAddAlternativa.textContent = "Adicionar alternativa";
    btnAddAlternativa.addEventListener("click", () => adicionarAlternativa(listaAlternativas));

    const btnRmvQuestao = document.createElement("button");
    btnRmvQuestao.textContent = "Excluir questão";
    btnRmvQuestao.addEventListener("click", () => removerQuestao(corpo));

    while (listaAlternativas.children.length < 2)
        adicionarAlternativa(listaAlternativas);

    corpo.append(numDaQuestao, txtDaQuestao, enunciadoQuestao, listaAlternativas, btnAddAlternativa, btnRmvQuestao);
    document.querySelector("#lista-questoes").appendChild(corpo, listaAlternativas);
}

function removerQuestao(corpo) {
    if (numQuestoes < 3) {
        alert("Um questionário deve ter pelo menos 2 questões");
        return;
    }
    corpo.remove();
    const listaNumDasQuestoes = document.querySelectorAll("#lista-questoes .num-questao");
    listaNumDasQuestoes.forEach((num, index) => {
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

    const corpo = document.createElement("div");
    corpo.classList.add("alternativa");

    const texto = document.createElement("textarea");
    texto.placeholder = "Texto da alternativa";
    expandirTextarea(texto);

    const ehCorreta = document.createElement("select");
    ehCorreta.add(new Option("Errada", "false"));
    ehCorreta.add(new Option("Correta", "true"));

    const btnRmvAlternativa = document.createElement("button");
    btnRmvAlternativa.textContent = "Remover alternativa";
    btnRmvAlternativa.addEventListener('click', () => removerAlternativa(corpo))

    corpo.append(texto, ehCorreta);
    listaAlternativas.appendChild(corpo);
}

function expandirTextarea(textarea) {
    textarea.addEventListener("input", function () {
        textarea.style.height = "auto";
        textarea.style.height = textarea.scrollHeight + "px";
        textarea.maxLength = String(MAX_CARACTERES_TXT_GRANDE);
    })
}

function enviar() {
    if (idxSegmento === -1 || idxSegmento < listaSegmentos.length - 1) {
        alert("Finalize todos os segmentos antes de enviar.");
        return;
    }

    const dados = {
        nome: form.nome.value || "",
        tipo: tipos[form.tipo.value] || "",
        valor: form.valor.value || "",
        dataEncerramento: form.data.value || "",
        horaEncerramento: form.horas.value || "",
        enunciado: "",
        questoes: "[]",
        tentativas: "1",
        tempoDeDuracao: "{\"numHoras\":1,\"numMinutos\":0}"
    };

    if (form.tipo.value === "questionario") {
        const listaQuestoes = document.querySelectorAll("#lista-questoes .questao");
        const questoesArray = [];

        listaQuestoes.forEach(q => {
            const texto = q.querySelector("textarea:nth-of-type(1)").value || "";
            const enunciado = q.querySelector("textarea:nth-of-type(2)").value || "";
            const alternativas = [];

            q.querySelectorAll("#lista-alternativas .alternativa").forEach(a => {
                alternativas.push({
                    texto: a.querySelector("textarea").value || "",
                    correta: a.querySelector("select").value === "true"
                });
            });

            questoesArray.push({ texto, enunciado, alternativas });
        });

        dados.questoes = JSON.stringify(questoesArray);

        const numTentativas = document.querySelector("#num-tentativas");
        dados.tentativas = numTentativas?.value || "1";

        const numHoras = document.querySelector("#num-horas")?.value || "1";
        const numMinutos = document.querySelector("#num-minutos")?.value || "0";
        dados.tempoDeDuracao = JSON.stringify({ numHoras, numMinutos });
    }

    else {
        const inputEnunciado = sessao.querySelector(".inputEnunciado");
        dados.enunciado = inputEnunciado.value;
    }

    fetch("http://localhost:6060/salvar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json ; charset=UTF-8"
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            console.log(JSON.stringify(dados));
            if (!response.ok) throw new Error("Erro ao enviar os dados");
            return response.json();
        })
        .then(data => {
            window.location.href = "../homeProfessor.html";
        })
        .catch(err => console.error("Falha no envio:", err));
}

function montarConfirmacao() {
    aparecerBotaoEnviar();
    const nome = sessao.querySelector(".nome");
    const tipo = sessao.querySelector(".tipo");
    const valor = sessao.querySelector(".valor");
    const dataEncerramento = sessao.querySelector(".dataEncerramento");
    const horaEncerramento = sessao.querySelector(".horaEncerramento");

    nome.innerHTML = "Nome: " + form.nome.value;
    tipo.innerHTML = "Tipo: " + tipos[form.tipo.value];
    valor.innerHTML = "Valor: " + form.valor.value + " pontos";
    dataEncerramento.innerHTML = "Data de Encerramento: " + form.data.value;
    horaEncerramento.innerHTML = "Hora de Encerramento: " + form.horas.value;

    if (form.tipo.value === "questionario") {
        const numQuestoesP = sessao.querySelector(".numQuestoes");
        numQuestoesP.innerHTML = `Número de questões: ${numQuestoes}`;
    }
    else {
        const enunciado = sessao.querySelector(".enunciado");
        const inputEnunciado = sessao.querySelector(".inputEnunciado");

        enunciado.innerHTML = `Enunciado: ${inputEnunciado.value}`;
    }
}

function initProdAtividade() {
    idxSegmento = -1;
    btnSair = document.getElementById("btn-sair");
    form = document.querySelector("form");
    btnAnterior = document.getElementById("btn-anterior");
    btnProximo = document.getElementById("btn-proximo");
    btnAddQuestao = document.getElementById("add-questao");
    btnEnviar = document.getElementById("btn-enviar")

    btnSair.addEventListener("click", confirmarSaidaDaPag);
    btnProximo.addEventListener("click", passarSegmento);
    btnAnterior.addEventListener("click", voltarSegmento);
    btnAddQuestao.addEventListener("click", adicionarQuestao);
    btnEnviar.addEventListener("click", enviar);
}