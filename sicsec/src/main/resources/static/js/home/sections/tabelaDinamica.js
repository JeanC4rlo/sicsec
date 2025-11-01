let idx = 0;
const notasBD = [
    {
        "ano": "2023",
        "materias": [["Português", "2", "2", "2", "2"], ["Matemática", "2", "2", "2", "2"]],
        "faltas": ["0", "0"],
    },
    {
        "ano": "2024",
        "materias": [["Português", "10", "10", "10", "10"], ["Matemática", "10", "10", "10", "10"]],
        "faltas": ["0", "0"],
    },
    {
        "ano": "2025",
        "materias": [["Português", "20", "20", "20", "20"], ["Matemática", "20", "20", "20", "20"]],
        "faltas": ["0", "0"],
    }
];

function passarAno(incremento) {
    let novoidx = idx + incremento;

    if (novoidx >= 0 && novoidx < notasBD.length) {
        idx = novoidx;
    }

    preencherTabela();
}

function preencherTabela() {
    const tabela = document.getElementById("tabelaBoletim");
    const tbody = tabela.querySelector("tbody");
    const ano = tabela.querySelector("div > p");
    const botaoEsq = document.querySelector("#botaoEsq");
    const botaoDir = document.querySelector("#botaoDir");

    let notas = notasBD[idx];

    tbody.innerHTML = "";
    ano.textContent = notas.ano;

    if (idx === 0) {
        botaoEsq.classList.add("desativado");
    } else {
        botaoEsq.classList.remove("desativado");
    }

    if (idx === notasBD.length - 1) {
        botaoDir.classList.add("desativado");
    } else {
        botaoDir.classList.remove("desativado");
    }

    for (let i = 0; i < notas.materias.length; i++) {
        const tr = document.createElement("tr");

        let soma = 0;
        notas.materias[i].forEach((v, j) => {
            const td = document.createElement("td");
            td.textContent = v;
            tr.appendChild(td);

            if (j !== 0) {
                soma += Number(v);
            }
        });

        const tdFaltas = document.createElement("td");
        tdFaltas.textContent = notas.faltas[i];
        tr.appendChild(tdFaltas);

        const tdNotaFinal = document.createElement("td");
        tdNotaFinal.textContent = soma;
        tr.appendChild(tdNotaFinal);

        const tdSituacao = document.createElement("td");
        tdSituacao.textContent = soma >= 60 ? "APROV" : "REPRV";
        tr.appendChild(tdSituacao);

        tbody.appendChild(tr);
    }
}


function initTabela() {

    const botaoBoletim = document.querySelector("#botaoAcessoBoletim");
    const botaoEsq = document.querySelector("#botaoEsq");
    const botaoDir = document.querySelector("#botaoDir");

    let anoAtual = new Date().getFullYear();
    idx = notasBD.findIndex(nota => nota.ano === String(anoAtual));

    if (idx === -1) idx = notasBD.length - 1;

    botaoBoletim.addEventListener('click', () => preencherTabela());
    botaoEsq.addEventListener('click', () => passarAno(-1));
    botaoDir.addEventListener('click', () => passarAno(1));
    preencherTabela();
}
