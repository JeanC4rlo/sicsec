/*const atividade = {
    nome: "Prova de Matemática",
    tipo: "questionario",
    valor: 10,
    dataEncerramento: "2025-10-31",
    questoes: [
        {
            texto: "Questão 1",
            enunciado: "Qual é a soma de 2 + 2?",
            alternativas: [
                { texto: "3", correta: false },
                { texto: "4", correta: true },
                { texto: "5", correta: false }
            ]
        },
        {
            texto: "Questão 2",
            enunciado: "Qual é a capital do Brasil?",
            alternativas: [
                { texto: "São Paulo", correta: false },
                { texto: "Brasília", correta: true },
                { texto: "Rio de Janeiro", correta: false }
            ]
        }
    ]
};*/

let atividade;

function formatarData(data) {
    data = data.split('-');
    return `${data[2]}/${data[1]}/${data[0]}`;
}

function criarMensagemIniciar(atividade) {
    const p = document.createElement("p");
    const btn = document.createElement("button");
    const p2 = document.createElement("p");
    if (atividade.tipo === "Questionário") {
        p.innerHTML = "Você está prestes a iniciar o questionário: " + atividade.nome;
        p2.innerHTML = "Tempo de duração: " + atividade.tempoDeDuracao.numHoras + "h:" + atividade.tempoDeDuracao.numMinutos + "min";
        btn.innerHTML = "Iniciar questionário";
        return;
    }
    p.innerHTML = atividade.nome;
}

function carregarAtividade() {
    atividade = JSON.parse(localStorage.getItem("atividade"));
    atividade.tempoDeDuracao = JSON.parse(atividade.tempoDeDuracao);
    return;

    const container = document.getElementById("atividade-container");
    container.innerHTML = `
                <div class="atividade-header">
                    <span>Nome: ${a.nome}</span>
                    <span>Tipo: ${a.tipo}</span>
                    <span>Valor: ${a.valor} ponto${a.valor > 1 ? 's' : ''}</span>
                    <span>Prazo: ${a.dataEncerramento}</span>
                </div>
            `;

    if (a.tipo === "questionario") {
        a.questoes.forEach((q, idx) => {
            const questaoDiv = document.createElement("div");
            questaoDiv.classList.add("questao");
            questaoDiv.innerHTML = `
                        <div class="enunciado">${idx + 1}. ${q.enunciado}</div>
                        <div class="alternativas">
                            ${q.alternativas.map((alt, i) => `
                                <label>
                                    <input type="radio" name="questao-${idx}" value="${i}"> ${alt.texto}
                                </label>
                            `).join("")}
                        </div>
                    `;
            container.appendChild(questaoDiv);
        });
    }

    if (a.tipo === "envTexto") {
        container.innerHTML += `<textarea placeholder="Digite sua resposta aqui..." rows="6"></textarea>`;
    }

    if (a.tipo === "envArquivo") {
        container.innerHTML += `<input type="file">`;
    }
}

function enviarRespostas() {
    alert("Respostas enviadas! (ainda sem backend)");
}

function primeiraTela() {
    if (atividade.tipo === "Questionário")
        carregarTelaQuestionario();
}

function carregarTelaQuestionario() {
    const container = document.getElementById("main-container");
    container.innerHTML = `
                <div class="first-screen-questionario">
                    <p>Nome: ${atividade.nome}</p>
                    <p>Você está prestes a iniciar o questionário: ${atividade.nome}</p>
                    <p>Valor: ${atividade.valor} ponto${atividade.valor > 1 ? 's' : ''}</p>
                    <p>Número de tentativas: ${atividade.tentativas}</p>
                    <p>Tempo da tentativa: ${atividade.tempoDeDuracao.numHoras} hora${atividade.tempoDeDuracao.numHoras > 1 ? 's' : ''} e ${atividade.tempoDeDuracao.numMinutos} minuto${atividade.tempoDeDuracao.numMinutos > 1 ? 's' : ''}</p>
                    <p>Prazo: ${formatarData(atividade.dataEncerramento)}</p>
                    <button id="btn-iniciar-tentativa">Iniciar tentativa</button>
                </div>
            `;
    const btnIniciarTentativa = document.getElementById("btn-iniciar-tentativa");
    btnIniciarTentativa.addEventListener("click", iniciarTentativa);
}

function iniciarTentativa() {
    
}

function initFazerAtividades() {
    carregarAtividade();

    const btnSair = document.getElementById("btn-sair");
    primeiraTela();
    btnSair.addEventListener('click', () => {
        if (confirm("Deseja realmente sair?"))
            window.location.href = "home.html";
    })
}

window.onload = initFazerAtividades;