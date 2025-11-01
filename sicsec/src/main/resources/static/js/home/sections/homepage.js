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
}