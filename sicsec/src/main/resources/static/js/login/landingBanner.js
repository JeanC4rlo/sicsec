const arrayCampus = [
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/araxa.jpg",
        campus: "Araxá",
        endereco: "Av. Min. Olavo Drummond, 25 - Amazonas, Araxá - MG, 38180-510",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/contagem.jpg",
        campus: "Contagem",
        endereco: "Alameda das Perdizes, 61 - Cabral, Contagem - MG, 32146-054",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/curvelo.jpg",
        campus: "Curvelo",
        endereco: "R. Raymundo Mattoso, 900 - Santa Rita, Curvelo - MG, 35790-000",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/divinopolis.jpg",
        campus: "Divinópolis",
        endereco: "R. Álvares de Azevedo, 400 - Bela Vista, Divinópolis - MG, 35503-822",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/gameleira.jpg",
        campus: "Gameleira",
        endereco: "Av. Amazonas, 5855 - Gameleira, Belo Horizonte - MG, 30510-000",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/leopoldina.jpg",
        campus: "Leopoldina",
        endereco: "R. José Péres, 558 - Centro, Leopoldina - MG, 36700-000",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/nepomuceno.jpg",
        campus: "Nepomuceno",
        endereco: "Av. Monsenhor Luís de Gonzaga, 103 - Centro, Nepomuceno - MG, 37250-000",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/nova-gameleira.jpg",
        campus: "Nova Gameleira",
        endereco: "Av. Amazonas, 7675 - Nova Gameleira, Belo Horizonte - MG, 30510-000",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/nova-suica.jpg",
        campus: "Nova Suíça",
        endereco: "Av. Amazonas, 5253 - Nova Suíça, Belo Horizonte - MG, 30421-169",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/timoteo.jpg",
        campus: "Timóteo",
        endereco: "R. Dezenove de Novembro, 121 - Centro, Timóteo - MG, 35180-008",
    },
    {
        imagem: "https://jeanc4rlo.github.io/sicsec-telas/imagens/campus/varginha.jpg",
        campus: "Varginha",
        endereco: "Av. dos Imigrantes, 1000 - Jardim Panorama, Varginha - MG, 37022-560",
    }
]

async function shuffle(array) {
    let arrayNovo = [];
    let arrayCopia = [...array];

    while(arrayCopia.length !== 0) {
        let index = Math.floor(Math.random() * arrayCopia.length);
        arrayNovo.push(arrayCopia[index]);
        arrayCopia.splice(index, 1);
    }

    console.log(arrayNovo);

    return arrayNovo;
}

let tempoDeRotacao = 5; // segundos

function inserirBanner(campusAtual) {
    const imagemCampus = document.querySelector(".imagem-campus");
    const campus = document.querySelector(".campus");
    const endereco = document.querySelector(".endereco");

    imagemCampus.src = campusAtual.imagem;
    campus.textContent = `Campus ${campusAtual.campus}`;
    endereco.textContent = campusAtual.endereco;
}

function rotacionarBanner(arrayCampus, tempoDeRotacao = 5) {
    let index = 0;
    setInterval(() => {
        inserirBanner(arrayCampus[index]);
        index = (index + 1) % arrayCampus.length;
    }, tempoDeRotacao * 1000);
}

rotacionarBanner(arrayCampus);
