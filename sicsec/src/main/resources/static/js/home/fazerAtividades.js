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
let statusAtividade;
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

async function carregarOuCriarTentativa() {
    try {
        let response = await fetch(`/atividade/${atividade.id}/tentativa-aberta`);
        if (!response.ok) throw new Error("Erro ao verificar tentativa aberta");

        const text = await response.text();
        const tentativa = text && text.trim().length > 0 ? JSON.parse(text) : null;

        if (tentativa && tentativa.id && tentativa.tempoRestante > 0) {
            statusAtividade = tentativa;
            console.log("Tentativa aberta encontrada:", tentativa);
        } else {
            const dados = {
                atividade: { id: atividade.id },
                horarioInicio: new Date().toISOString(),
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

            statusAtividade = await response.json();
            console.log("Nova tentativa criada:", statusAtividade);
        }
        tempoTotal = statusAtividade.tempoRestante || converterTempoDaAtividade();
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
    temporizador = setInterval(() => {
        let horas = Math.floor(tempoTotal / 3600);
        let minutos = Math.floor((tempoTotal % 3600) / 60);
        let segundos = Math.floor((tempoTotal % 60));

        timer.innerHTML = `${horas.toString().padStart(2, "0")}:${minutos
            .toString()
            .padStart(2, "0")}:${segundos.toString().padStart(2, "0")}`;

        tempoTotal -= 1;

        if (tempoTotal == -1) timeOut();
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

async function enviarRespostas() {
    if (!checkQuestaoMarcada()) return;
    const alternativasMarcadas = document.querySelectorAll("input:checked");
    const promessas = Array.from(alternativasMarcadas).map((alternativa, index) => {
        const respostaAluno = {
            atividade: { id: atividade.id },
            statusAtividade: { id: statusAtividade.id },
            numQuestao: index,
            alternativaMarcada: alternativa.value,
            correta: null
        };
        return fetch("/salvar/resposta", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(respostaAluno)
        })
            .then(resp => {
                if (!resp.ok) throw new Error("Falha ao salvar resposta");
                return resp.json();
            });
    });

    try {
        await Promise.all(promessas);
        alert("Respostas enviadas com sucesso!");
        window.location.href = "../home.html";
    } catch (err) {
        console.error("Erro ao enviar respostas:", err);
        alert("Ocorreu um erro ao salvar as respostas. Tente novamente.");
    }
}


async function timeOut() {
    clearInterval(temporizador);

    if (statusAtividade && statusAtividade.id) {
        await fecharTentativa();
    }

    alert("O tempo desta tentativa acabou. Reinicie a página para começar uma nova");
}

function carregarTelaQuestionario() {
    console.log(tentativasFeitas, atividade.tentativas);
    containerPrincipal.innerHTML = `
    <div class="first-screen-questionario">
      <p>Nome: ${atividade.nome}</p>
      <p>Você está prestes a iniciar o questionário: ${atividade.nome}</p>
      <p>Valor: ${atividade.valor} ponto${atividade.valor > 1 ? "s" : ""}</p>
      <p>Número de tentativas: ${atividade.tentativas}</p>
      <p>Tempo da tentativa: ${atividade.tempoDeDuracao.numHoras} hora${atividade.tempoDeDuracao.numHoras > 1 ? "s" : ""
        } e ${atividade.tempoDeDuracao.numMinutos} minuto${atividade.tempoDeDuracao.numMinutos > 1 ? "s" : ""
        }</p>
      <p>Prazo: ${formatarData(atividade.dataEncerramento)}</p>
      <button id="btn-iniciar-tentativa">Iniciar tentativa</button>
    </div>
  `;

    const btnIniciar = document.getElementById("btn-iniciar-tentativa");
    btnIniciar.addEventListener("click", () => {
        if (tentativasFeitas > atividade.tentativas) {
            telaQuestionarioJaFeito();
            return;
        }
        carregarOuCriarTentativa();
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
    const textarea = document.createElement("textarea");
    const btnEnviar = document.createElement("button");

    nome.innerHTML = atividade.nome;
    enunciado.innerHTML = atividade.enunciado;
    btnEnviar.textContent = "Enviar";
    textarea.maxLength = LIMITE_CARACTERES;

    carregarArquivos();

    containerPrincipal.append(nome, enunciado, textarea, btnEnviar);
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
            carregarTelaRedacao();
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
        const response = await fetch(`/atualizar-status-tentativa/${statusAtividade.id}/fechar-tentativa`, {
            method: "PATCH"
        });
        if (!response.ok) throw new Error("Erro ao fechar a tentativa");
    } catch (err) {
        console.error(err);
    }
}

async function atualizarTempo() {
    try {
        const response = await fetch(`/atualizar-status-tentativa/${statusAtividade.id}/atualizar-tempo`, {
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
    if (tempoTotal > 0)
        atualizarTempo();
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
    btnEnviar.addEventListener("click", enviarRespostas);

    containerPrincipal.append(btnAnterior, btnProximo, btnEnviar);
}

async function carregarArquivos() {
    const response = await fetch(`http://localhost:6060/arquivos/${atividade.id}`);
    if (!response.ok) return console.error("Não foi possível carregar os arquivos");
    const arquivos = await response.json();

    const lista = document.createElement("list");
    lista.innerHTML = "";

    arquivos.forEach(nome => {
        const ul = document.createElement("ul");
        const link = document.createElement("a");
        ul.append(link);

        link.href = `http://localhost:6060/download/${nome}`;
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
        if (confirm("Deseja realmente sair?")) window.location.href = "home.html";
    });
}

window.onload = initFazerAtividades;