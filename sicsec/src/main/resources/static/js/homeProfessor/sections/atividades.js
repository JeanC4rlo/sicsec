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

function criarNovaAtividade() {

    const linha = document.createElement("tr");
    const codigo = document.createElement("td");
    const nome = document.createElement("td");
    const tipo = document.createElement("td");
    const valor = document.createElement("td");
    const dataExp = document.createElement("td");
    const dataCri = document.createElement("td");

    linha.id = "trem";

    codigo.innerHTML = "CÓDIGO";
    nome.innerHTML = "NOME";
    tipo.innerHTML = "TIPO";
    valor.innerHTML = "VALOR";
    dataExp.innerHTML = "DATAEXP (x dias)";
    dataCri.innerHTML = "DATACRI";

    linha.append(codigo, nome, valor, tipo, dataExp, dataCri);
    corpoTable.append(linha);
}

function initAtividades() {
    barraDePesquisa = document.getElementById("barra-de-pesquisa");;
    btnAddAtividade = document.getElementById("add-atividade");
    tabela = document.getElementById("tabela-atividades");
    corpoTable = tabela.querySelector("tbody")
    

    btnAddAtividade.addEventListener('click', () => {
        window.location.href = "../homeProfessorSections/prodAtividade.html";
    });
}
