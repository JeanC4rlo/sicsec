const noticias = [
    {
        detalhe: '15/10/2025 - PORTUGUÊS',
        texto: 'Livro Memórias Póstumas de Brás Cubas'
    },
    {
        detalhe: '16/10/2025 - BIOLOGIA',
        texto: 'Aula no auditório 202 P19'
    },
    {
        detalhe: '17/10/2025 - QUÍMICA',
        texto: 'Apresentação de Trabalhos'
    }
];

let indiceAtual = 0;
let intervaloSlideshow;
const tempoIntervalo = 5000;

function atualizarNoticia(indice) {
    const noticia = noticias[indice];

    const detalheEl = document.getElementById('detalheNoticia');
    const textoEl = document.getElementById('textoNoticia');

    detalheEl.textContent = noticia.detalhe;
    textoEl.textContent = noticia.texto;
}

function pararSlideshow() {
    clearInterval(intervaloSlideshow);
    const pararBtn = document.getElementById('btnParar');
    pararBtn.textContent = 'INICIAR';
    pararBtn.classList.add('ativo');
}

function avancarIndiceAutomatico() {
    indiceAtual++;
    if (indiceAtual >= noticias.length) {
        indiceAtual = 0;
    }
    atualizarNoticia(indiceAtual);
}

function iniciarSlideshow() {
    pararSlideshow();

    intervaloSlideshow = setInterval(avancarIndiceAutomatico, tempoIntervalo);

    const pararBtn = document.getElementById('btnParar');
    pararBtn.textContent = 'PARAR';
    pararBtn.classList.remove('ativo');
}

function navegarManual(direcao) {
    pararSlideshow();

    indiceAtual += direcao;

    if (indiceAtual >= noticias.length) {
        indiceAtual = 0;
    } else if (indiceAtual < 0) {
        indiceAtual = noticias.length - 1;
    }

    atualizarNoticia(indiceAtual);
}

function calcularDistanciaDias(data) {
    return Math.round((new Date(data).getTime() - new Date().getTime()) / (1000 * 3600 * 24));
}

function formatarDataEHora(data, hora) {
    let distanciaEmDias = calcularDistanciaDias(data);
    data = data.split("-");
    let mensagem = `${data[2]}/${data[1]}/${data[0]} ${hora}<br>`;
    if(distanciaEmDias > 1)
        mensagem += `(${distanciaEmDias} dia${(distanciaEmDias > 1) ? 's)' : ')'}`
    else
        mensagem += "hoje";
    return mensagem
}

function irParaFazerAtividades(id) {
    fetch(`/api/atividade/${id}`)
        .then(response => response.json())
        .then(atividade => {
            localStorage.setItem("atividade", JSON.stringify(atividade));
            window.location.href = "../../fazerAtividades.html";
        })
}

function atualizarAtividades(atividade) {
    if (calcularDistanciaDias(atividade.dataEncerramento) < 0) {
        return;
    }
    const corpoTable = document.querySelector(".minhas-atividades tbody");
    const linha = document.createElement("tr");
    const data = document.createElement("td");
    const infoAtividade = document.createElement("td");

    linha.addEventListener('click', () => irParaFazerAtividades(atividade.id))

    data.classList.add("data-col");
    data.innerHTML = formatarDataEHora(atividade.dataEncerramento, atividade.horaEncerramento);

    const disciplina = document.createElement("span");
    disciplina.classList.add("disciplina");
    const avaliacao = document.createElement("span");
    avaliacao.classList.add("avaliacao");

    disciplina.innerHTML = "DISCIPLINA";
    avaliacao.innerHTML = `${atividade.nome}<br>${atividade.tipo}: ${atividade.valor} ponto`;
    if (atividade.valor > 1) avaliacao.innerHTML += "s";

    infoAtividade.append(disciplina, avaliacao);

    linha.append(data, infoAtividade);
    corpoTable.append(linha);
}

function carregarAtividades() {
    fetch("/api/atividade/home-atividades")
        .then(response => {
            if (!response.ok) throw new Error("Falha ao carregar as atividades");
            return response.json();
        })
        .then(dados => {
            dados.forEach(atividade => {
                atualizarAtividades(atividade);
            });
        })
        .catch(err => {
            console.log("Erro:", err);
        })
}

function initHomepage() {
    // Adicionar event listeners
    const prevBtn = document.getElementById('prevNoticia');
    const nextBtn = document.getElementById('nextNoticia');
    const pararBtn = document.getElementById('btnParar');

    prevBtn.addEventListener('click', () => navegarManual(-1));
    nextBtn.addEventListener('click', () => navegarManual(1));
    pararBtn.addEventListener('click', () => {
        if (pararBtn.textContent === 'PARAR') {
            pararSlideshow();
        } else {
            iniciarSlideshow();
        }
    });

    // Inicialização
    atualizarNoticia(indiceAtual);
    iniciarSlideshow();
    carregarAtividades();
}