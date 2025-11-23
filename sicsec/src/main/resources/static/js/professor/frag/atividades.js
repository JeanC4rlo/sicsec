let barraDePesquisa;
let btnAddAtividade;
let tabela;
let corpoTable;

const input = (placeholder, type = "text", name = "") => {
    const el = document.createElement("input");
    el.type = type;
    el.placeholder = placeholder;
    if (name) el.name = name;
    return el;
};

function formatarDataEHora(data) {
    let distanciaEmDias = Math.round((new Date(data).getTime() - new Date().getTime()) / (1000 * 3600 * 24));
    data = data.split("-");
    let mensagem = `${data[2]}/${data[1]}/${data[0]}<br>(${distanciaEmDias} dia`;
    mensagem += (distanciaEmDias > 1) ? "s)" : ")";
    return mensagem
}

function atividadeAindaDisponivel(data, linha) {
    if((new Date(data).getTime() - new Date().getTime()) / (1000 * 3600 * 24) <= 0) {
        linha.classList.add("encerrada");
        return false;
    }
    return true;
}

function preencherTabelaAtividades(atividade) {

    const linha = document.createElement("tr");
    const codigo = document.createElement("td");
    const nome = document.createElement("td");
    const tipo = document.createElement("td");
    const valor = document.createElement("td");
    const dataEnc = document.createElement("td");
    const dataCri = document.createElement("td");

    linha.classList.add("atividade");

    codigo.innerHTML = atividade.id;
    nome.innerHTML = atividade.nome;
    tipo.innerHTML = atividade.tipo;
    valor.innerHTML = atividade.valor;
    if(atividadeAindaDisponivel(atividade.dataEncerramento, linha)) {
        dataEnc.innerHTML = formatarDataEHora(atividade.dataEncerramento);
    }
    else {
        dataEnc.innerHTML = "Atividade encerrada";
    }
    dataCri.innerHTML = "DATACRI";

    linha.append(codigo, nome, tipo, valor, dataEnc, dataCri);
    corpoTable.append(linha);
}

function carregarAtividades() {
    fetch("/api/atividade/atividades")
    .then(response => {
        if(!response.ok) throw new Error("Erro ao carregar as atividades");
        return response.json();
    })
    .then(dados => {
        dados.forEach(atividade => {
            preencherTabelaAtividades(atividade);
        });
    })
    .catch(err => {
        console.log("Erro:", err);
    })
}

function initAtividades() {
    barraDePesquisa = document.getElementById("barra-de-pesquisa");;
    btnAddAtividade = document.getElementById("add-atividade");
    tabela = document.getElementById("tabela-atividades");
    corpoTable = tabela.querySelector("tbody")
    

    btnAddAtividade.addEventListener('click', () => {
        window.location.href = "/producao-atividades";
    });

    carregarAtividades();
}

initAtividades();
