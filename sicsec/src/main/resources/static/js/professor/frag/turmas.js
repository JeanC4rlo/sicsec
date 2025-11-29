let listaDesempenhosHTML;

async function carregarDesempenho() {
    try {
        const resp = await fetch("/api/desempenho/desempenhos");
        if (!resp.ok) throw new Error("Erro ao carregar as respostas");

        const desempenhos = await resp.json();
        console.log(JSON.stringify(desempenhos, null, 2));
        desempenhos.forEach(desempenho => preencherLista(desempenho));

    } catch (erro) {
        console.error("Erro:", erro);
    }
}

function preencherLista(desempenho) {
    const itemLista = document.createElement("li");
    const nome = document.createElement("span");
    const atividade = document.createElement("span");
    const tipo = document.createElement("span");
    const nota = document.createElement("span");

    nome.textContent = "NOME ALUNO";
    atividade.textContent = desempenho.nomeAtividade;
    tipo.textContent = desempenho.tipoAtividade;
    nota.textContent = desempenho.nota != null? desempenho.nota + "/" + desempenho.valorAtividade : "A definir";

    itemLista.append(nome, atividade, tipo, nota);
    listaDesempenhosHTML.append(itemLista);

    montarDescricao(desempenho.tipoAtividade, itemLista, listaDesempenhosHTML, desempenho.desempenhoId);
}

function initTurmas() {
    listaDesempenhosHTML = document.getElementById("lista-desempenhos");
    carregarDesempenho();
}
