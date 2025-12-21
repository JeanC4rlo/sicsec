function initSectionTurmas() {
    let tabelaDesempenhos;
    let corpoTable;
    const tabelaSemDados = `
    <table id="tabela-desempenhos">
        <caption>Desempenho da turma</caption>
        <thead>
            <tr>
                <th>Aluno</th>
                <th>Turma</th>
                <th>Atividade</th>
                <th>Nº Tentativa</th>
                <th>Tipo</th>
                <th>Nota</th>
            </tr>
        </thead>
        <tbody>
        <tr id="msg-sem-atividade">
                        <td colspan="6"><p><i>Nenhuma atividade criada</i></p></td>
                    </tr>
        </tbody>
    </table>
    `

    function initTurmas() {
        tabelaDesempenhos = document.getElementById("tabela-desempenhos");
        corpoTable = tabelaDesempenhos.querySelector("tbody");
        carregarDesempenho();
    }

    async function carregarDesempenho() {
        try {
            const resp = await fetch("/api/desempenho/desempenhos");
            if (!resp.ok) throw new Error("Erro ao carregar as respostas");
            tabelaDesempenhos.innerHTML = tabelaSemDados;
            const desempenhos = await resp.json();
            if (desempenhos.length > 0) {
                corpoTable.innerHTML = "";
                desempenhos.sort((a, b) => {
                    if (a.nomeAtividade.localeCompare(b.nomeAtividade) == 0)
                        return a.nomeAluno.localeCompare(b.nomeAluno);
                    return a.nomeAtividade.localeCompare(b.nomeAtividade);
                })
                desempenhos.forEach(desempenho => preencherTabela(desempenho));
                pesquisarPorAtividade();
            }
        } catch (erro) {
            console.error("Erro:", erro);
        }
    }

    function criarElement(tipo, valor = null, classe = null, id = null) {
        const el = document.createElement(tipo);
        if (valor) el.innerHTML = valor;
        if (classe) el.classList.add(classe);
        if (id) el.id = id;
        return el;
    }

    function preencherFiltroTurma() {

    }

    function preencherTabela(desempenho) {
        const linha = criarElement("tr", null, "atividade");
        const nome = criarElement("td", desempenho.nomeAluno, "nome-aluno");
        const turma = criarElement("td", desempenho.turmaAluno, "turma-aluno");
        const tipo = criarElement("td", desempenho.tipoAtividade, "tipo");
        const atividade = criarElement("td", desempenho.nomeAtividade, "atividade");
        const numTentativa = criarElement("td", desempenho.numTentativa, "num-tentativa");
        const nota = criarElement("td", null, "nota");
        if (desempenho.notaAluno != null) nota.textContent = desempenho.notaAluno + "/" + desempenho.valorAtividade
        else nota.append(botaoAvaliar(desempenho));

        linha.append(nome, turma, atividade, numTentativa, tipo, nota);
        corpoTable.append(linha);
        tabelaDesempenhos.append(corpoTable);
    }

    function pesquisarPorAtividade() {
        const barraDePesquisa = document.getElementById("barra-de-pesquisa-desempenho");
        console.log(barraDePesquisa);

        barraDePesquisa.addEventListener("input", () => {

            const texto = normalizar(barraDePesquisa.value);
            const linhas = tabelaDesempenhos.querySelectorAll("tr");
            console.log(linhas);

            linhas.forEach(linha => {
                const celulaNome = linha.querySelector(".nome-aluno");
                if (!celulaNome) return;
                console.log(celulaNome);

                const nomeAtvd = normalizar(celulaNome.textContent);

                linha.style.display = nomeAtvd.includes(texto) ? '' : 'none';
            });
        });
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