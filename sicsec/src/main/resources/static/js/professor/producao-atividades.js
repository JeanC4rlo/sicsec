let btnSair, form, btnAnterior, btnProximo, btnAddQuestao, btnEnviar;
let sessao;
let listaSegmentos;
let idxSegmento = -1;
let numQuestoes = 0;
let inputDeArquvios;

const MAX_NUM_ALTERNATIVAS = 6;
const MAX_CARACTERES_TXT_PEQUENO = 252;
const MAX_CARACTERES_TXT_GRANDE = 2000;

const tipos = {
    questionario: "Questionário",
    redacao: "Redação",
    envArquivo: "Envio de Arquivo"
};

window.onload = initProdAtividade;

function initProdAtividade() {
    form = document.querySelector("form");
    btnSair = document.getElementById("btn-sair");
    btnAnterior = document.getElementById("btn-anterior");
    btnProximo = document.getElementById("btn-proximo");
    btnAddQuestao = document.getElementById("add-questao");
    btnEnviar = document.getElementById("btn-enviar");

    btnSair.addEventListener("click", confirmarSaidaDaPag);
    btnProximo.addEventListener("click", passarSegmento);
    btnAnterior.addEventListener("click", voltarSegmento);
    btnAddQuestao.addEventListener("click", adicionarQuestao);
    btnEnviar.addEventListener("click", enviar);

    idxSegmento = -1;
}

function ativarForm() {
    sessao.classList.remove("ativo");
    form.classList.remove("inativo");
}

function definirSessao() {
    sessao = document.querySelector(`#section-${form.tipo.value}`);
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

function validarContainer(container) {
    const campos = container.querySelectorAll("input, textarea, select");
    for (const campo of campos) {
        if (!campo.value.trim()) {
            validarCampo(campo);
            return false;
        }
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

function validarData(data) {
    const [ano, mes, dia] = data.split("-").map(Number);
    const dataObj = new Date(ano, mes - 1, dia);
    return dataObj.getTime() > Date.now();
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
    if (idxSegmento === 0) {
        definirSessao();
        listaSegmentos[idxSegmento].classList.add("ativo");
        return;
    }

    listaSegmentos[idxSegmento - 1].classList.remove("ativo");
    listaSegmentos[idxSegmento].classList.add("ativo");
}

function passarSegmento() {
    if (idxSegmento === -1) {
        for (let campo of form.elements) {
            if (!validarCampo(campo)) return;
        }

        if (!validarData(form.data.value)) {
            alert("A data inserida é inválida");
            destacarCampo(form.data);
            return;
        }
        passar();
        if (form.tipo.value == "redacao" || form.tipo.value == "envArquivo") enviarArquivo();
        return;
    }

    if (idxSegmento >= listaSegmentos.length - 1) return;

    switch (form.tipo.value) {
        case "questionario":
            switch (idxSegmento) {
                case 0:
                    if (numQuestoes < 2) {
                        alert("Um questionário deve ter pelo menos 2 questões");
                        return;
                    }
                    if (!validarContainer(sessao.querySelector("#lista-questoes"))) return;
                    passar();
                    montarConfirmacao();
                    break;
                case 1:
                    if (!validarLista(sessao.querySelector("#duracao-e-tentativas"), { selectors: ["input"] })) return;
                    passar();
                    montarConfirmacao();
                    break;
            }
            break;
        case "redacao":
        case "envArquivo":
            if (!validarContainer(sessao.querySelector(".elaboracao"))) return;
            passar();
            montarConfirmacao();
            break;
    }
}

function voltarSegmento() {
    if (idxSegmento < 0) return;

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

function enviarArquivo() {
    inputDeArquvios = document.createElement("input");
    inputDeArquvios.type = "file";
    inputDeArquvios.style.display = "none";
    inputDeArquvios.id = "fileInput";
    inputDeArquvios.multiple = true;
    inputDeArquvios.accept = "application/zip,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain";
    sessao.appendChild(inputDeArquvios);

    let listaArquivos = document.createElement("ul");
    listaArquivos.id = "lista-arquivos";
    sessao.appendChild(listaArquivos);

    const btnEnvArquivo = sessao.querySelector(".btn-enviar-arquivo");
    btnEnvArquivo.addEventListener("click", () => inputDeArquvios.click());

    inputDeArquvios.addEventListener("change", () => {
        listaArquivos.innerHTML = "";
        for (let i = 0; i < inputDeArquvios.files.length; i++) {
            let li = document.createElement("li");
            li.textContent = inputDeArquvios.files[i].name;
            listaArquivos.appendChild(li);
        }
    });
}


function adicionarQuestao() {
    numQuestoes++;

    const corpo = document.createElement("div");
    corpo.classList.add("questao", "ativo");

    const numDaQuestao = document.createElement("p");
    numDaQuestao.innerHTML = `Questão ${numQuestoes}`;
    numDaQuestao.classList.add("num-questao");

    const txtDaQuestao = document.createElement("textarea");
    txtDaQuestao.placeholder = "Texto da questão";
    txtDaQuestao.maxLength = MAX_CARACTERES_TXT_GRANDE;
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

    while (listaAlternativas.children.length < 2) adicionarAlternativa(listaAlternativas);

    corpo.append(numDaQuestao, txtDaQuestao, enunciadoQuestao, listaAlternativas, btnAddAlternativa, btnRmvQuestao);
    document.querySelector("#lista-questoes").appendChild(corpo);
}

function removerQuestao(corpo) {
    if (numQuestoes < 3) {
        alert("Um questionário deve ter pelo menos 2 questões");
        return;
    }
    corpo.remove();
    const listaNumDasQuestoes = document.querySelectorAll("#lista-questoes .num-questao");
    listaNumDasQuestoes.forEach((num, index) => num.innerHTML = `Questão ${index + 1}`);
    numQuestoes--;
}

function adicionarAlternativa(listaAlternativas) {
    const numAlternativas = listaAlternativas.children.length;
    if (numAlternativas >= MAX_NUM_ALTERNATIVAS) {
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
    btnRmvAlternativa.addEventListener("click", () => corpo.remove());

    corpo.append(texto, ehCorreta, btnRmvAlternativa);
    listaAlternativas.appendChild(corpo);
}

function expandirTextarea(textarea) {
    textarea.addEventListener("input", () => {
        textarea.style.height = "auto";
        textarea.style.height = textarea.scrollHeight + "px";
    });
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
        tempoDeDuracao: JSON.stringify({ numHoras: 1, numMinutos: 0 }),
        tipoTimer: ""
    };

    if (form.tipo.value === "questionario") {
        const listaQuestoes = document.querySelectorAll("#lista-questoes .questao");
        const questoesArray = [];

        listaQuestoes.forEach(q => {
            const texto = q.querySelector("textarea:nth-of-type(1)").value || "";
            const enunciado = q.querySelector("textarea:nth-of-type(2)").value || "";
            const alternativas = [];
            let idxCorreta;

            q.querySelectorAll("#lista-alternativas .alternativa").forEach((a, idx) => {
                alternativas.push({
                    texto: a.querySelector("textarea").value || ""
                });
                if (a.querySelector("select").value === "true") idxCorreta = idx;
            });

            questoesArray.push({ texto, enunciado, alternativas, idxCorreta });
        });

        dados.questoes = JSON.stringify(questoesArray);
        dados.tentativas = document.querySelector("#num-tentativas")?.value || "1";

        const numHoras = document.querySelector("#num-horas")?.value || "1";
        const numMinutos = document.querySelector("#num-minutos")?.value || "0";
        dados.tempoDeDuracao = JSON.stringify({ numHoras, numMinutos });

        dados.tipoTimer = document.querySelector("#tipo-timer")?.value || "interrompivel";
    }
    else {
        const inputEnunciado = sessao.querySelector(".inputEnunciado");
        dados.enunciado = inputEnunciado.value;
    }

    const formData = new FormData();
    formData.append("atividade", new Blob([JSON.stringify(dados)], { type: "application/json" }));

    if (form.tipo.value != "questionario" && inputDeArquvios && inputDeArquvios.files.length > 0) {
        for (const file of inputDeArquvios.files) {
            if (file.size > 5 * 1024 * 1024) {
                alert(`Arquivo muito grande: ${file.name}`);
                return;
            }
            const extensoesPermitidas = [".txt", ".pdf", ".docx", ".zip"];
            const nome = file.name.toLowerCase();
            if (!extensoesPermitidas.some(ext => nome.endsWith(ext))) {
                alert(`Tipo de arquivo não permitido: ${file.name}`);
                return;
            }
        }
        for (const file of inputDeArquvios.files) {
            formData.append("arquivos", file);
        }
    }

    fetch("/api/atividade/salvar", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao enviar os dados");
            return response.json();
        })
        .then(data => {
            window.location.href = "/homeProfessor";
        })
        .catch(err => console.error("Falha no envio:", err));
}


function montarConfirmacao() {
    btnEnviar.classList.remove("inativo");

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
        sessao.querySelector(".numQuestoes").innerHTML = `Número de questões: ${numQuestoes}`;
    } else {
        const enunciado = sessao.querySelector(".enunciado");
        const inputEnunciado = sessao.querySelector(".inputEnunciado");
        enunciado.innerHTML = `Enunciado: ${inputEnunciado.value}`;
    }
}

function confirmarSaidaDaPag() {
    if (confirm("Tem certeza que deseja sair da página? Os dados da atividade serão perdidos?")) {
        window.location.href = "../homeProfessor.html";
    }
}