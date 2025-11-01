const path = "../data/livros.json";
let livrosCarregados = [];

function criarCardAcervo(livro) {
    const card = document.createElement("div");

    const capa = document.createElement("img");
    capa.src = `../imagens/livros/${livro.capa}.png`;
    card.appendChild(capa);

    const titulo = document.createElement("h3");
    titulo.innerHTML = livro.titulo;
    titulo.classList = "title";
    card.appendChild(titulo);

    const info = document.createElement("div");
    card.appendChild(info);

    const autor = document.createElement("p");
    autor.innerHTML = `<strong>Autor:</strong> ${livro.autor}`;
    info.appendChild(autor);

    const ano = document.createElement("p");
    ano.innerHTML = `<strong>Ano:</strong> ${livro.ano_publicacao}`;
    info.appendChild(ano);

    const genero = document.createElement("p");
    genero.innerHTML = `<strong>Gênero:</strong> ${livro.genero}`;
    info.appendChild(genero);

    const divBotoes = document.createElement("div");
    card.appendChild(divBotoes);

    const botaoReservar = document.createElement("button");
    botaoReservar.innerHTML = "Reservar";
    divBotoes.appendChild(botaoReservar);

    const botaoDetalhes = document.createElement("button");
    botaoDetalhes.innerHTML = "Detalhes";
    divBotoes.appendChild(botaoDetalhes);

    card.classList = "livro-card";
    info.classList = "info";
    divBotoes.classList = "buttons";

    return card;
}

async function loadAcervo() {
    let response = await fetch(path);
    livrosCarregados = await response.json();
    renderizarLivros(livrosCarregados);
}

function renderizarLivros(lista) {
    const container = document.getElementById("resultado-acervo");
    container.innerHTML = ""; // limpa antes de renderizar
    lista.forEach(livro => {
        const card = criarCardAcervo(livro);
        container.appendChild(card);
    });
}

function filtrarLivros() {
    const campo = document.getElementById("campoPesquisa").value;
    const texto = document.getElementById("textoPesquisa").value.toLowerCase();

    const resultado = livrosCarregados.filter(livro => {
        if (campo === "todos") {
            return Object.values(livro).some(valor =>
                typeof valor === "string" && valor.toLowerCase().includes(texto)
            );
        } else {
            const valorCampo = livro[campo];
            return typeof valorCampo === "string" && valorCampo.toLowerCase().includes(texto);
        }
    });

    renderizarLivros(resultado);
}

function initAcervo() {
    loadAcervo();

    const btnPesquisar = document.getElementById("btnPesquisar");
    btnPesquisar.addEventListener("click", filtrarLivros);
}
