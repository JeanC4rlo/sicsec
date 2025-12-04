function initSectionTurmas() {
    let tabelaDesempenhos;

    function initTurmas() {
        tabelaDesempenhos = document.getElementById("tabela-desempenhos");
        carregarDesempenho();
    }

    async function carregarDesempenho() {
        try {
            const resp = await fetch("/api/desempenho/desempenhos");
            if (!resp.ok) throw new Error("Erro ao carregar as respostas");

            const desempenhos = await resp.json();
            console.log(JSON.stringify(desempenhos, null, 2));
            desempenhos.forEach(desempenho => preencherTabela(desempenho));

        } catch (erro) {
            console.error("Erro:", erro);
        }
    }

    function preencherTabela(desempenho) {
        const linha = document.createElement("tr");
        const nome = document.createElement("td");
        const atividade = document.createElement("td");
        const tipo = document.createElement("td");
        const nota = document.createElement("td");

        nome.textContent = desempenho.nomeAluno;
        atividade.textContent = desempenho.nomeAtividade;
        tipo.textContent = desempenho.tipoAtividade;
        if (desempenho.notaAluno != null) nota.textContent = desempenho.notaAluno + "/" + desempenho.valorAtividade
        else nota.append(botaoAvaliar(desempenho));

        linha.append(nome, atividade, tipo, nota);
        tabelaDesempenhos.append(linha);
    }

    function normalizar(texto) {
        return texto
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase();
    }

    function redirectParaPaginaAvaliacao(desempenhoId, tipoAtividade) {
        tipoAtividade = normalizar(tipoAtividade);
        window.location.href = `/html/professor/avaliacao.html?id=${desempenhoId}&tipo=${tipoAtividade}`;
    }

    function botaoAvaliar(desempenho) {
        const btn = document.createElement("button");
        btn.classList.add("botao-avaliar");
        btn.textContent = "Avaliar";
        btn.addEventListener("click", () => redirectParaPaginaAvaliacao(desempenho.desempenhoId, desempenho.tipoAtividade))
        return btn;
    }

    initTurmas();
}

initSectionTurmas();
document.querySelector("button[data-id='turmas']").addEventListener("click", initSectionTurmas);