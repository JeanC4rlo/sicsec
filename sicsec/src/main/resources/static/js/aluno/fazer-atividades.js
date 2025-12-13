const state = {
    atividade: null,
    questoes: [],
    numQuestoes: 0,
    tempoTotal: 0,
    numTentativasFeitas: 0,
    objTentativa: null,
    temporizador: null,
    LIMITE_CARACTERES: 2000,
    containerPrinci: null,
    timerEncerrado: false
}

const htmlDOM = {
    containerPrincipal: null,
    btnAnterior: null,
    btnProximo: null,
    btnEnviar: null
}

const LIMITES = {
    numCaracteres: 2000
}

function formatarData(data) {
    data = data.split("-");
    return `${data[2]}/${data[1]}/${data[0]}`;
}

function carregarAtividade() {
    state.atividade = JSON.parse(localStorage.getItem("atividade"));
    state.atividade.questoes = JSON.parse(state.atividade.questoes);
    state.atividade.tempoDeDuracao = JSON.parse(state.atividade.tempoDeDuracao);
}

function checkQuestaoMarcada() {
    const selecionado = state.questoes[state.numQuestoes].querySelector(`input[name="alternativa_questao_${state.numQuestoes + 1}"]:checked`);
    if (!selecionado) {
        alert("Marque alguma alternativa antes de continuar!");
        return false;
    }
    return true;
}

function checkRedacaoEscrita() {
    const redacao = document.getElementById("textarea-redacao");
    if (!redacao.value) {
        alert("Você não escreveu sua redação ainda!");
        return false;
    }
    return true;
}

function caracteresRestantes() {
    const numCaracteres = document.getElementById("textarea-redacao").value.length;
    return LIMITES.numCaracteres - numCaracteres;
}

async function carregarOuCriarTentativa() {
    try {
        let response = await fetch(`/api/tentativa/atividade/${state.atividade.id}/tentativa-aberta`);
        if (!response.ok) throw new Error("Erro ao verificar tentativa aberta");

        const text = await response.text();
        const _tentativa = text && text.trim().length > 0 ? JSON.parse(text) : null;

        if (_tentativa) {
            if (state.atividade.tipoTimer == "none" || (state.atividade.tipoTimer != "none" && _tentativa.tempoRestante > 0)) {
                state.objTentativa = _tentativa;
                console.log("Tentativa aberta encontrada:", state.objTentativa);
            }
        } else {
            const dados = {
                atividade: { id: state.atividade.id },
                horarioInicio: new Date().toLocaleString("sv-SE",
                    { timeZone: "America/Sao_Paulo" }).replace(" ", "T") + ".000000",
                tempoRestante: converterTempoDaAtividade(),
                numTentativa: state.numTentativasFeitas + 1,
                aberta: true
            }
            response = await fetch("/api/tentativa/salvar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });

            if (!response.ok) throw new Error("Erro ao criar nova tentativa");

            state.objTentativa = await response.json();
            console.log("Nova tentativa criada:", state.objTentativa);
        }
        state.tempoTotal = state.objTentativa.tempoRestante || converterTempoDaAtividade();
    } catch (err) {
        console.error(err);
        alert("Não foi possível carregar a tentativa");
    }
}

function AddBotaoEnviar() {
    htmlDOM.btnEnviar.classList.remove("inativo");
    htmlDOM.btnProximo.classList.add("inativo");
}

function RemoverBotaoEnviar() {
    htmlDOM.btnEnviar.classList.add("inativo");
    htmlDOM.btnProximo.classList.remove("inativo");
}

function criarTimer(timer) {
    switch (state.atividade.tipoTimer) {
        case "continuo":
            criarTimerContinuo(timer);
            break;
        case "interrompivel":
            criarTimerInterrompivel(timer);
            break;
    }
}

function criarTimerInterrompivel(timer) {
    state.temporizador = setInterval(() => {
        let horas = Math.floor(state.tempoTotal / 3600);
        let minutos = Math.floor((state.tempoTotal % 3600) / 60);
        let segundos = Math.floor((state.tempoTotal % 60));

        timer.innerHTML = `${horas.toString().padStart(2, "0")}:${minutos.toString()
            .padStart(2, "0")}:${segundos.toString().padStart(2, "0")}`;

        state.tempoTotal--;

        if (state.tempoTotal <= 0) timeOut();
    }, 1000);
}

async function criarTimerContinuo(timer) {
    const resposta = await fetch(`/api/tentativa/tempo-restante/${state.objTentativa.id}`);
    let tempoRestante = await resposta.json();

    state.temporizador = setInterval(() => {
        if (tempoRestante <= 0) timeOut();
        else {
            let horas = Math.floor(tempoRestante / 3600);
            let minutos = Math.floor((tempoRestante % 3600) / 60);
            let segundos = Math.floor((tempoRestante % 60));

            timer.innerHTML = `${horas.toString().padStart(2, "0")}:${minutos.toString()
                .padStart(2, "0")}:${segundos.toString().padStart(2, "0")}`;

            tempoRestante--;
        }
    }, 1000);
}

function criarOptions(questao, container) {
    questao.alternativas.forEach((alternativa, idx) => {
        const containerAlternativa = document.createElement("div");
        containerAlternativa.classList.add("alternativa");
        const opcao = document.createElement("input");
        const label = document.createElement("label");

        opcao.type = "radio";
        opcao.name = "alternativa_questao_" + (state.questoes.length + 1);
        opcao.value = idx;
        opcao.id = `alt_${idx}`;

        label.htmlFor = opcao.id;
        label.textContent = alternativa.texto;
        containerAlternativa.append(opcao, label);
        container.append(containerAlternativa);
    });
}

function montarQuestao(questao) {
    const containerQuestao = document.createElement("div");
    containerQuestao.classList.add("questao");

    const containerTexto = document.createElement("div");
    containerTexto.id = "container-texto";

    const texto = document.createElement("p");
    containerTexto.append(texto);

    const enunciado = document.createElement("p");

    texto.innerHTML = questao.texto;
    enunciado.innerHTML = questao.enunciado;

    containerQuestao.append(containerTexto, enunciado);
    criarOptions(questao, containerQuestao);

    containerQuestao.classList.add("inativo");
    state.questoes.push(containerQuestao);
    htmlDOM.containerPrincipal.append(containerQuestao);
    if (state.numQuestoes === state.atividade.questoes.length - 1) AddBotaoEnviar();
}

function navegarEntreQuestoes(sentido) {
    if (sentido == 1 && !checkQuestaoMarcada()) return;

    if (state.numQuestoes + sentido >= 0 && state.numQuestoes + sentido < state.atividade.questoes.length) {
        state.questoes[state.numQuestoes].classList.add("inativo");
        state.numQuestoes += sentido;
        const p = document.querySelector("#numQuestao");
        p.innerHTML = "Questão " + (state.numQuestoes + 1);
        if (state.numQuestoes === state.atividade.questoes.length - 1) AddBotaoEnviar();
        else if (!htmlDOM.btnEnviar.classList.contains("inativo")) RemoverBotaoEnviar();
        state.questoes[state.numQuestoes].classList.remove("inativo");
    }
}

function converterTempoParaSegundos(horas = 0, minutos = 0, segundos = 0) {
    horas *= 3600;
    minutos *= 60;
    return horas + minutos + segundos;
}

function converterTempoDaAtividade() {
    if (!state.atividade.tempoDeDuracao) return null;
    return converterTempoParaSegundos(Number(state.atividade.tempoDeDuracao.numHoras), Number(state.atividade.tempoDeDuracao.numMinutos));
}

function montarListaAlternativasMarcadas(alternativas) {
    const lista = [];
    alternativas.forEach((alt, idx) => {
        lista.push({
            numQuestao: idx,
            alternativa: alt.value,
            estaCorreta: null
        });
    })
    return lista;
}

async function enviarQuestionario() {
    if (!checkQuestaoMarcada()) return;

    if (state.timerEncerrado) {
        window.location.reload();
        return;
    }

    clearInterval(state.temporizador);

    const alternativasMarcadas = document.querySelectorAll("input:checked");

    const respostaAluno = {
        atividade: { id: state.atividade.id },
        tentativa: { id: state.objTentativa.id },
        alternativasMarcadas: montarListaAlternativasMarcadas(alternativasMarcadas),
        textoRedacao: null,
        arquivoId: null
    };

    const resposta = await fetch("/api/resposta/enviar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(respostaAluno)
    });

    if (!resposta.ok)
        throw new Error("Erro desconhecido do servidor");

    try {
        alert("Respostas enviadas com sucesso!");
        fecharTentativa();
        montarPaginaResultado(await resgatarNota());
    } catch (err) {
        console.error("Erro ao enviar respostas:", err);
        alert("Ocorreu um erro ao salvar as respostas. Tente novamente.");
    }
}

async function enviarRedacao() {
    if (!checkRedacaoEscrita()) return;

    if (state.timerEncerrado) {
        window.location.reload();
        return;
    }

    clearInterval(state.temporizador);

    console.log(document.getElementById("textarea-redacao").value);

    const respostaAluno = {
        atividade: { id: state.atividade.id },
        tentativa: { id: state.objTentativa.id },
        alternativasMarcadas: null,
        textoRedacao: document.getElementById("textarea-redacao").value,
        arquivoId: null
    };

    const resposta = await fetch("/api/resposta/enviar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(respostaAluno)
    });

    if (!resposta.ok)
        throw new Error("Erro desconhecido do servidor");
    try {
        alert("Redação enviada com sucesso!");
        fecharTentativa();
        window.location.href = "/home";
    } catch (err) {
        console.error("Erro ao enviar a redação:", err);
        alert("Ocorreu um erro ao enviar sua redação. Tente novamente.");
    }
}

async function enviarArquivo() {
    const inputArquivo = document.getElementById("input-arquivo");
    if (inputArquivo == null || inputArquivo.files.length == 0) return;


    const respostaAluno = {
        atividade: { id: state.atividade.id },
        tentativa: { id: state.objTentativa.id },
        alternativasMarcadas: null,
        textoRedacao: null,
        arquivoId: null
    };

    const formData = new FormData();

    if (inputArquivo && inputArquivo.files.length > 0) {
        const file = inputArquivo.files[0];
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

        formData.append("resposta", new Blob([JSON.stringify(respostaAluno)], { type: "application/json" }));
        formData.append("arquivo", file);
    }

    const resposta = await fetch("/api/resposta/enviar/arquivo", {
        method: "POST",
        body: formData
    });

    if (!resposta.ok)
        throw new Error("Erro desconhecido do servidor");
    try {
        alert("Arquivo enviado com sucesso!");
        fecharTentativa();
        window.location.href = "/home";
    } catch (err) {
        console.error("Erro ao enviar o arquivo:", err);
        alert("Ocorreu um erro ao enviar seu arquivo. Tente novamente.");
    }
}

async function resgatarNota() {
    const resposta = await fetch(`/api/desempenho/tentativa/${state.objTentativa.id}/nota`);
    const valor = await resposta.json();
    return Number(valor);
}

function montarPaginaResultado(nota) {
    htmlDOM.containerPrincipal.innerHTML = `
    <div id="resultado">
        <h1>Resposta enviada</h1>
        <p>Nota: ${nota}</p>
    </div>
    `
}

async function timeOut() {
    clearInterval(state.temporizador);

    if (state.objTentativa && state.objTentativa.id) {
        await fecharTentativa();
    }
    alert("O tempo desta tentativa acabou. Reinicie a página para começar uma nova");
    state.timerEncerrado = true;
}

function telaInfoAtividade() {
    htmlDOM.containerPrincipal.innerHTML = `
    <div class="first-screen">
      <h2>${state.atividade.nome}</h2>
      <p>Você está prestes a iniciar a atividade: ${state.atividade.nome}</p>
      <p>Valor: <strong>${state.atividade.valor} ponto${state.atividade.valor > 1 ? "s" : ""}</strong></p>
      <p>Tipo: <strong>${state.atividade.tipo}</strong></p>
      <p>Número máximo de tentativas: ${state.atividade.tentativas}</p>
      <p>Tentativas restantes: ${state.atividade.tentativas - state.numTentativasFeitas}</p>
      
      <p>Término: ${formatarData(state.atividade.dataEncerramento)}</p>
      <button id="btn-iniciar-tentativa">Iniciar tentativa</button>
    </div>
  `;

    const btnIniciar = document.getElementById("btn-iniciar-tentativa");
    btnIniciar.addEventListener("click", async () => {
        if (state.numTentativasFeitas >= state.atividade.tentativas) {
            telaAtividadeJaFeita();
            return;
        }
        htmlDOM.containerPrincipal.innerHTML = "";
        iniciarTentativa();
    });
}

function telaAtividadeJaFeita() {
    htmlDOM.containerPrincipal.innerHTML = `
    <div class="first-screen">
      <p>Você já fez esta atividade</p>
      <p>A atividade <strong>${state.atividade.nome}</strong> já foi feita e enviada por você</p>
      <p>A nota será desponibilizada em breve</p>
    </div>
  `;
}

function carregarTelaRedacao() {
    if (state.numTentativasFeitas > state.atividade.tentativas) {
        telaAtividadeJaFeita();
        return;
    }
    const nome = document.createElement("p");
    const labelEnunciado = document.createElement("h4");
    const enunciado = document.createElement("p");
    const textareaRedacao = document.createElement("textarea");
    const numCaracteresRestantes = document.createElement("p");

    numCaracteresRestantes.style.fontSize = "15px";

    nome.innerHTML = state.atividade.nome;
    labelEnunciado.innerHTML = "Enunciado:";
    enunciado.innerHTML = state.atividade.enunciado;
    textareaRedacao.maxLength = LIMITES.numCaracteres;
    textareaRedacao.placeholder = "Escreva seu texto aqui!";

    textareaRedacao.id = "textarea-redacao";
    textareaRedacao.addEventListener("input", () => {
        numCaracteresRestantes.innerHTML = caracteresRestantes()
    });

    htmlDOM.containerPrincipal.append(nome, labelEnunciado, enunciado, textareaRedacao, numCaracteresRestantes);

    carregarArquivos();
    carregarOuCriarTentativa();
    addBotaoEnviarResposta();
}

async function carregarTelaEnvioArquivo() {
    await contarTentativas();
    if (state.numTentativasFeitas >= state.atividade.tentativas) {
        telaAtividadeJaFeita();
        return;
    }
    const nome = document.createElement("p");
    const enunciado = document.createElement("p");
    const inputArquivo = document.createElement("input");
    htmlDOM.btnEnviar = document.createElement("button");

    nome.innerHTML = state.atividade.nome;
    enunciado.innerHTML = state.atividade.enunciado;
    htmlDOM.btnEnviar.textContent = "Enviar";

    inputArquivo.id = "input-arquivo";
    inputArquivo.type = "file";

    carregarArquivos();
    carregarOuCriarTentativa();

    htmlDOM.containerPrincipal.append(nome, enunciado, inputArquivo, htmlDOM.btnEnviar);

    htmlDOM.btnEnviar.addEventListener("click", enviarArquivo);
}

async function primeiraTela() {
    switch (state.atividade.tipo) {
        case ("Questionário"):
            await contarTentativas();
            telaInfoAtividade();
            break;

        case ("Redação"):
            await contarTentativas();
            telaInfoAtividade();
            break;
        case ("Envio de Arquivo"):
            carregarTelaEnvioArquivo();
            break;
    }
}

function formatarDataHora(date) {
    const ano = date.getFullYear();
    const mes = String(date.getMonth() + 1).padStart(2, "0");
    const dia = String(date.getDate()).padStart(2, "0");
    const horas = String(date.getHours()).padStart(2, "0");
    const minutos = String(date.getMinutes()).padStart(2, "0");
    const segundos = String(date.getSeconds()).padStart(2, "0");

    return `${ano}-${mes}-${dia} ${horas}:${minutos}:${segundos}`;
}

async function contarTentativas() {
    await fetch(`/api/tentativa/atividade/${state.atividade.id}/num-tentativas`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao consultar o número de tentativas");
            }
            return response.json();
        })
        .then(dado => {
            state.numTentativasFeitas = dado;
        })
        .catch(err => console.error("Falha na requisição:", err));
}

async function fecharTentativa() {
    try {
        const response = await fetch(`/api/tentativa/fechar/${state.objTentativa.id}`, {
            method: "PATCH"
        });
        if (!response.ok) throw new Error("Erro ao fechar a tentativa");
    } catch (err) {
        console.error(err);
    }
}

async function atualizarTempo() {
    try {
        const response = await fetch(`/api/tentativa/atualizar-timer/${state.objTentativa.id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ tempoRestante: state.tempoTotal })
        });

        if (!response.ok) throw new Error("Não foi possível atualizar o tempo");

        const tentativaAtualizada = await response.json();
        console.log("Tentativa atualizada:", tentativaAtualizada);
    } catch (err) {
        console.error(err);
    }
}

function salvarStatusTentativa() {
    clearInterval(state.temporizador);

    const dados = {
        atividade: state.atividade.id,
        horarioInicio: new Date().toISOString(),
        tempoRestante: state.tempoTotal,
        numTentativa: state.numTentativasFeitas + 1,
        aberta: true
    };

    fetch("/api/tentativa/salvar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dados),
        keepalive: true
    });
}

function tratamentoPreFechamentoDaPagina() {
    if (state.tempoTotal > 0) {
        if (state.atividade.tipoTimer === "interrompivel") {
            atualizarTempo();
        }
    }
}

function createBotao(nome, func, ...params) {
    const btn = document.createElement("button");
    btn.textContent = nome;
    btn.addEventListener("click", () => func(...params));
    return btn;
}

function addBotoesNavegacaoEBotaoEnviarResposta() {
    htmlDOM.btnAnterior = createBotao("Anterior", navegarEntreQuestoes, -1);
    htmlDOM.btnProximo = createBotao("Próximo", navegarEntreQuestoes, 1);
}

function addBotaoEnviarResposta(ativo = true) {
    if (state.atividade.tipo == "Questionário")
        htmlDOM.btnEnviar = createBotao("Enviar", enviarQuestionario);
    if (state.atividade.tipo == "Redação")
        htmlDOM.btnEnviar = createBotao("Enviar", enviarRedacao);
    else if (state.atividade.tipo == "Envio de Arquivo")
        htmlDOM.btnEnviar = createBotao("Enviar", enviarArquivo);
    if (!ativo) htmlDOM.btnEnviar.classList.add("inativo");
}

function addCabecalho() {
    const cabecalho = document.createElement("div");
    cabecalho.id = "cabecalho";
    if (state.atividade.tipo == "Questionário") {
        const numQuestao = document.createElement("p");
        numQuestao.id = "numQuestao";
        numQuestao.innerHTML = "Questão 1";

        cabecalho.append(numQuestao);
    }
    const timer = document.createElement("p");
    cabecalho.append(timer);
    criarTimer(timer);
    htmlDOM.containerPrincipal.append(cabecalho);
}

async function iniciarTentativa() {
    await carregarOuCriarTentativa();
    window.addEventListener("beforeunload", tratamentoPreFechamentoDaPagina);
    addCabecalho();
    if (state.atividade.tipo == "Questionário") {
        addBotoesNavegacaoEBotaoEnviarResposta();
        addBotaoEnviarResposta(false);
        state.atividade.questoes.forEach(questao => {
            montarQuestao(questao);
        })
        state.questoes[0].classList.remove("inativo")
        htmlDOM.containerPrincipal.append(htmlDOM.btnAnterior, htmlDOM.btnProximo);
        htmlDOM.containerPrincipal.append(htmlDOM.btnEnviar);
        return;
    }
    else if (state.atividade.tipo == "Redação") {
        carregarTelaRedacao();
        addBotaoEnviarResposta();
        htmlDOM.containerPrincipal.append(htmlDOM.btnEnviar);
    }
    else {
        carregarTelaEnvioArquivo();
    }
    addBotaoEnviarResposta();
}

async function carregarArquivos() {
    const response = await fetch(`/api/arquivo/listar/atividade/${state.atividade.id}`);
    if (!response.ok) throw new Error("Não foi possível carregar os arquivos");
    const arquivos = await response.json();

    const lista = document.createElement("list");
    lista.innerHTML = "";

    arquivos.forEach(arquivo => {
        const ul = document.createElement("ul");
        const link = document.createElement("a");
        ul.append(link);

        link.href = `/api/arquivo/${arquivo.id}/atividade/${arquivo.donoId}`;
        link.textContent = arquivo.nomeSalvo;
        link.target = "_blank";
        lista.append(ul);
    });
    htmlDOM.containerPrincipal.append(lista);
}

function initFazerAtividades() {
    htmlDOM.containerPrincipal = document.getElementById("main-container");
    carregarAtividade();
    primeiraTela();

    const btnSair = document.getElementById("btn-sair");
    btnSair.addEventListener("click", () => {
        if (confirm("Deseja realmente sair?")) window.location.href = "/home";
    });
}

window.onload = initFazerAtividades;