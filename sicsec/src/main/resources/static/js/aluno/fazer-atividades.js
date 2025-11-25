let atividade;
let contador = 0;
let containerPrincipal;
let tempoTotal = 0;
let btnAnterior;
let btnProximo;
let btnEnviar;
let listaContainers = [];
let temporizador;
let tentativasFeitas;
let tempoAcabou = false;
let tentativa;
const LIMITE_CARACTERES = 2000;

function formatarData(data) {
    data = data.split("-");
    return `${data[2]}/${data[1]}/${data[0]}`;
}

function carregarAtividade() {
    atividade = JSON.parse(localStorage.getItem("atividade"));
    atividade.questoes = JSON.parse(atividade.questoes);
    atividade.tempoDeDuracao = JSON.parse(atividade.tempoDeDuracao);
}

function checkQuestaoMarcada() {
    const selecionado = listaContainers[contador].querySelector(`input[name="alternativa_questao_${contador + 1}"]:checked`);
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
    return LIMITE_CARACTERES - numCaracteres;
}

async function carregarOuCriarTentativa() {
    try {
        let response = await fetch(`/atividade/${atividade.id}/tentativa-aberta`);
        console.log(response);
        if (!response.ok) throw new Error("Erro ao verificar tentativa aberta");

        const text = await response.text();
        const _tentativa = text && text.trim().length > 0 ? JSON.parse(text) : null;

        if (_tentativa && _tentativa.id && _tentativa.tempoRestante > 0) {
            tentativa = _tentativa;
            console.log("Tentativa aberta encontrada:", tentativa);
        } else {
            const dados = {
                atividade: { id: atividade.id },
                horarioInicio: new Date().toLocaleString("sv-SE",
                    { timeZone: "America/Sao_Paulo" }).replace(" ", "T") + ".000000",
                tempoRestante: converterTempoDaAtividade(),
                numTentativa: tentativasFeitas + 1,
                aberta: true
            }
            response = await fetch("/salvar-status-atividade", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });

            if (!response.ok) throw new Error("Erro ao criar nova tentativa");

            tentativa = await response.json();
            console.log("Nova tentativa criada:", tentativa);
        }
        tempoTotal = tentativa.tempoRestante || converterTempoDaAtividade();
    } catch (err) {
        console.error(err);
        alert("Não foi possível carregar a tentativa");
    }
}

function AddBotaoEnviar() {
    btnEnviar.classList.remove("inativo");
    btnProximo.classList.add("inativo");
}

function RemoverBotaoEnviar() {
    btnEnviar.classList.add("inativo");
    btnProximo.classList.remove("inativo");
}

function criarTimer(timer) {
    switch (atividade.tipoTimer) {
        case "continuo":
            criarTimerContinuo(timer);
            break;
        case "interrompivel":
            criarTimerInterrompivel(timer);
            break;
    }
}

function criarTimerInterrompivel(timer) {
    temporizador = setInterval(() => {
        let horas = Math.floor(tempoTotal / 3600);
        let minutos = Math.floor((tempoTotal % 3600) / 60);
        let segundos = Math.floor((tempoTotal % 60));

        timer.innerHTML = `${horas.toString().padStart(2, "0")}:${minutos.toString()
            .padStart(2, "0")}:${segundos.toString().padStart(2, "0")}`;

        tempoTotal--;

        if (tempoTotal <= 0) timeOut();
    }, 1000);
}

async function criarTimerContinuo(timer) {
    const resposta = await fetch(`/tempo-restante/${tentativa.id}`);
    let tempoRestante = await resposta.json();

    temporizador = setInterval(() => {
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
        const opcao = document.createElement("input");
        const label = document.createElement("label");

        opcao.type = "radio";
        opcao.name = "alternativa_questao_" + (listaContainers.length + 1);
        opcao.value = idx;
        opcao.id = `alt_${idx}`;

        label.htmlFor = opcao.id;
        label.textContent = alternativa.texto;

        container.append(opcao, label, document.createElement("br"));
    });
}

function montarQuestao(questao) {
    const container = document.createElement("div");

    const texto = document.createElement("p");
    const enunciado = document.createElement("p");

    texto.innerHTML = questao.texto;
    enunciado.innerHTML = questao.enunciado;

    container.append(texto, enunciado);
    criarOptions(questao, container);

    container.classList.add("inativo");
    listaContainers.push(container);
    containerPrincipal.append(container);
}

function navegarEntreQuestoes(sentido) {
    if (sentido == 1 && !checkQuestaoMarcada()) return;

    if (contador + sentido >= 0 && contador + sentido < atividade.questoes.length) {
        listaContainers[contador].classList.add("inativo");
        contador += sentido;
        const p = document.querySelector("#numQuestao");
        p.innerHTML = "Questão " + (contador + 1);
        if (contador === atividade.questoes.length - 1) AddBotaoEnviar();
        else if (!btnEnviar.classList.contains("inativo")) RemoverBotaoEnviar();
        listaContainers[contador].classList.remove("inativo");
    }
}


function converterTempoParaSegundos(horas = 0, minutos = 0, segundos = 0) {
    horas *= 3600;
    minutos *= 60;
    return horas + minutos + segundos;
}

function converterTempoDaAtividade() {
    return converterTempoParaSegundos(Number(atividade.tempoDeDuracao.numHoras), Number(atividade.tempoDeDuracao.numMinutos));
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

async function enviarRespostasQuestionario() {
    if (!checkQuestaoMarcada()) return;

    if (tempoAcabou) {
        window.location.reload();
        return;
    }

    clearInterval(temporizador);

    const alternativasMarcadas = document.querySelectorAll("input:checked");

    const respostaAluno = {
        atividade: { id: atividade.id },
        tentativa: { id: tentativa.id },
        alternativasMarcadas: montarListaAlternativasMarcadas(alternativasMarcadas),
        textoRedacao: null,
        nomeArquivo: null
    };

    const resposta = await fetch("/enviar/questionario", {
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

    /*if (tempoAcabou) {
        window.location.reload();
        return;
    }*/

    //clearInterval(temporizador);

    console.log(document.getElementById("textarea-redacao").value);

    const respostaAluno = {
        atividade: { id: atividade.id },
        tentativa: null,
        alternativasMarcadas: null,
        textoRedacao: document.getElementById("textarea-redacao").value,
        nomeArquivo: null
    };

    const resposta = await fetch("/enviar/redacao", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(respostaAluno)
    });

    if (!resposta.ok)
        throw new Error("Erro desconhecido do servidor");
    try {
        alert("Redação enviada com sucesso!");
        //fecharTentativa();
        window.location.href = "/home";
    } catch (err) {
        console.error("Erro ao enviar a redação:", err);
        alert("Ocorreu um erro ao enviar sua redação. Tente novamente.");
    }
}

async function enviarArquivo() {
    const inputArquivo = document.getElementById("input-arquivo");
    if (inputArquivo.files.length == 0) return;


    const respostaAluno = {
        atividade: { id: atividade.id },
        tentativa: null,
        alternativasMarcadas: null,
        textoRedacao: null,
        nomeArquivo: null
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
        respostaAluno.nomeArquivo = file.name;
        formData.append("resposta", new Blob([JSON.stringify(respostaAluno)], { type: "application/json" }));
        formData.append("arquivo", file);
    }

    const resposta = await fetch("/enviar/arquivo", {
        method: "POST",
        body: formData
    });

    if (!resposta.ok)
        throw new Error("Erro desconhecido do servidor");
    try {
        alert("Redação enviada com sucesso!");
        //fecharTentativa();
        window.location.href = "/home";
    } catch (err) {
        console.error("Erro ao enviar o arquivo:", err);
        alert("Ocorreu um erro ao enviar seu arquivo. Tente novamente.");
    }
}

async function resgatarNota() {
    const resposta = await fetch(`/conferir-nota/${atividade.id}/${tentativa.id}`);
    const valor = await resposta.json();
    return Number(valor);
}

function montarPaginaResultado(nota) {
    containerPrincipal.innerHTML = `
    <h1>Resposta enviada</h1>
    <p>Nota: ${nota}</p>
    `
}

async function timeOut() {
    clearInterval(temporizador);

    if (tentativa && tentativa.id) {
        await fecharTentativa();
    }
    alert("O tempo desta tentativa acabou. Reinicie a página para começar uma nova");
    tempoAcabou = true;
}

function carregarTelaQuestionario() {
    containerPrincipal.innerHTML = `
    <div class="first-screen-questionario">
      <h2>${atividade.nome}</h2>
      <p>Você está prestes a iniciar o questionário: ${atividade.nome}</p>
      <p>Valor: <strong>${atividade.valor} ponto${atividade.valor > 1 ? "s" : ""}</strong></p>
      <p>Número máximo de tentativas: ${atividade.tentativas}</p>
      <p>Tentativas restantes: ${atividade.tentativas - tentativasFeitas}</p>
      <p>Tempo por tentativa: ${atividade.tempoDeDuracao.numHoras} hora${atividade.tempoDeDuracao.numHoras > 1 ? "s" : ""
        } e ${atividade.tempoDeDuracao.numMinutos} minuto${atividade.tempoDeDuracao.numMinutos > 1 ? "s" : ""
        }</p>
      <p>Término: ${formatarData(atividade.dataEncerramento)}</p>
      <button id="btn-iniciar-tentativa">Iniciar tentativa</button>
    </div>
  `;

    const btnIniciar = document.getElementById("btn-iniciar-tentativa");
    btnIniciar.addEventListener("click", async () => {
        if (tentativasFeitas >= atividade.tentativas) {
            telaQuestionarioJaFeito();
            return;
        }
        await carregarOuCriarTentativa();
        iniciarTentativa();
    });
}

function telaQuestionarioJaFeito() {
    containerPrincipal.innerHTML = `
    <div class="first-screen-questionario">
      <p>Você já fez este questionário</p>
      <p>O questionário <strong>${atividade.nome}</strong> já foi feito e enviado por você</p>
      <p>A nota será desponibilizada em breve</p>
    </div>
  `;
}

function carregarTelaRedacao() {
    const nome = document.createElement("p");
    const enunciado = document.createElement("p");
    const textareaRedacao = document.createElement("textarea");
    const btnEnviar = document.createElement("button");
    const numCaracteresRestantes = document.createElement("p");

    numCaracteresRestantes.style.fontSize = "15px";

    nome.innerHTML = atividade.nome;
    enunciado.innerHTML = atividade.enunciado;
    btnEnviar.textContent = "Enviar";
    textareaRedacao.maxLength = LIMITE_CARACTERES;

    textareaRedacao.id = "textarea-redacao";
    textareaRedacao.addEventListener("input", () => {
        numCaracteresRestantes.innerHTML = caracteresRestantes()
    });

    carregarArquivos();

    containerPrincipal.append(nome, enunciado, textareaRedacao, numCaracteresRestantes, btnEnviar);

    btnEnviar.addEventListener("click", enviarRedacao);
}

function carregarTelaEnvioArquivo() {
    const nome = document.createElement("p");
    const enunciado = document.createElement("p");
    const inputArquivo = document.createElement("input");
    const btnEnviar = document.createElement("button");

    nome.innerHTML = atividade.nome;
    enunciado.innerHTML = atividade.enunciado;
    btnEnviar.textContent = "Enviar";

    inputArquivo.id = "input-arquivo";
    inputArquivo.type = "file";

    carregarArquivos();

    containerPrincipal.append(nome, enunciado, inputArquivo, btnEnviar);

    btnEnviar.addEventListener("click", enviarArquivo);
}

async function primeiraTela() {
    switch (atividade.tipo) {
        case ("Questionário"):
            await contarTentativas();
            carregarTelaQuestionario();
            break;

        case ("Redação"):
            carregarTelaRedacao();
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
    await fetch(`/atividade/${atividade.id}/tentativas`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao consultar o número de tentativas");
            }
            return response.json();
        })
        .then(dado => {
            tentativasFeitas = dado;
        })
        .catch(err => console.error("Falha na requisição:", err));
}

async function fecharTentativa() {
    try {
        const response = await fetch(`/atualizar-status-tentativa/${tentativa.id}/fechar-tentativa`, {
            method: "PATCH"
        });
        if (!response.ok) throw new Error("Erro ao fechar a tentativa");
    } catch (err) {
        console.error(err);
    }
}

async function atualizarTempo() {
    try {
        const response = await fetch(`/atualizar-status-tentativa/${tentativa.id}/atualizar-tempo`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ tempoRestante: tempoTotal })
        });

        if (!response.ok) throw new Error("Não foi possível atualizar o tempo");

        const tentativaAtualizada = await response.json();
        console.log("Tentativa atualizada:", tentativaAtualizada);
    } catch (err) {
        console.error(err);
    }
}

function salvarStatusTentativa() {
    clearInterval(temporizador);

    const dados = {
        atividade: atividade.id,
        horarioInicio: new Date().toISOString(),
        tempoRestante: tempoTotal,
        numTentativa: tentativasFeitas + 1,
        aberta: true
    };

    fetch("/salvar-status-atividade", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dados),
        keepalive: true
    });
}

function tratamentoPreFechamentoDaPagina() {
    if (tempoTotal > 0) {
        if (atividade.tipoTimer === "interrompivel") {
            atualizarTempo();
        }
    }
    else
        fecharTentativa();
}

function iniciarTentativa() {
    window.addEventListener("beforeunload", tratamentoPreFechamentoDaPagina);

    containerPrincipal.innerHTML = "";

    const cabecalho = document.createElement("div");
    const numQuestao = document.createElement("p");
    const timer = document.createElement("p");

    cabecalho.id = "cabecalho";

    numQuestao.innerHTML = "Questão 1";
    numQuestao.id = "numQuestao";

    cabecalho.append(numQuestao, timer);
    containerPrincipal.append(cabecalho);

    criarTimer(timer);

    atividade.questoes.forEach(questao => {
        montarQuestao(questao);
    })

    listaContainers[0].classList.remove("inativo")

    btnAnterior = document.createElement("button");
    btnProximo = document.createElement("button");
    btnEnviar = document.createElement("button");

    btnAnterior.textContent = "Anterior";
    btnProximo.textContent = "Próximo";
    btnEnviar.textContent = "Enviar";

    btnEnviar.classList.add("inativo");

    btnAnterior.addEventListener("click", () => navegarEntreQuestoes(-1));
    btnProximo.addEventListener("click", () => navegarEntreQuestoes(1));
    btnEnviar.addEventListener("click", enviarRespostasQuestionario);

    containerPrincipal.append(btnAnterior, btnProximo, btnEnviar);
}

async function carregarArquivos() {
    const response = await fetch(`/api/atividade/${atividade.id}/arquivos`);
    if (!response.ok) return console.error("Não foi possível carregar os arquivos");
    const arquivos = await response.json();

    const lista = document.createElement("list");
    lista.innerHTML = "";

    arquivos.forEach(nome => {
        const ul = document.createElement("ul");
        const link = document.createElement("a");
        ul.append(link);

        link.href = `/api/atividade/download/${encodeURIComponent(nome)}`;
        link.textContent = nome;
        link.target = "_blank";
        lista.append(ul);
    });

    containerPrincipal.append(lista);
}

function initFazerAtividades() {
    containerPrincipal = document.getElementById("main-container");
    carregarAtividade();
    primeiraTela();

    const btnSair = document.getElementById("btn-sair");
    btnSair.addEventListener("click", () => {
        if (confirm("Deseja realmente sair?")) window.location.href = "/home";
    });
}

window.onload = initFazerAtividades;