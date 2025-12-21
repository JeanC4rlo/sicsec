function initSectionTurmas() {
    let tabelaDesempenhos;
    const tabelaSemDados = `
    <table id="tabela-desempenhos">
        <caption>Desempenho da turma</caption>
        <thead>
            <tr>
                <th>Aluno</th>
                <th>Atividade</th>
                <th>Nº Tentativa</th>
                <th>Tipo</th>
                <th>Nota</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
    `

    function initTurmas() {
        tabelaDesempenhos = document.getElementById("tabela-desempenhos");
        carregarDesempenho();
    }

    async function carregarDesempenho() {
        try {
            const resp = await fetch("/api/desempenho/desempenhos");
            if (!resp.ok) throw new Error("Erro ao carregar as respostas");
            tabelaDesempenhos.innerHTML = tabelaSemDados;
            const desempenhos = await resp.json();
            desempenhos.sort((a, b) => {
                if (a.nomeAtividade.localeCompare(b.nomeAtividade) == 0)
                    return a.nomeAluno.localeCompare(b.nomeAluno);
                return a.nomeAtividade.localeCompare(b.nomeAtividade);
            })
            desempenhos.forEach(desempenho => preencherTabela(desempenho));

        } catch (erro) {
            console.error("Erro:", erro);
        }
    }

    function preencherTabela(desempenho) {
        const linha = document.createElement("tr");
        const nome = document.createElement("td");
        const atividade = document.createElement("td");
        const numTentativa = document.createElement("td");
        const tipo = document.createElement("td");
        const nota = document.createElement("td");

        nome.textContent = desempenho.nomeAluno;
        atividade.textContent = desempenho.nomeAtividade;
        numTentativa.textContent = desempenho.numTentativa;
        tipo.textContent = desempenho.tipoAtividade;
        if (desempenho.notaAluno != null) nota.textContent = desempenho.notaAluno + "/" + desempenho.valorAtividade
        else nota.append(botaoAvaliar(desempenho));

        linha.append(nome, atividade, numTentativa, tipo, nota);
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