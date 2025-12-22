function initSectionAtividadesProfessor() {
    let barraDePesquisa;
    let btnAddAtividade;
    let tabelaAtividades;
    let corpoTable;

    /*function formatarData(data) {
        data = data.split("-");
        return `${data[2]}/${data[1]}/${data[0]}`;
    }*/

    function calcularDistanciaData(data, hora = "00:00", tipo = "dias") {
        let denominador = 1;
        switch (tipo) {
            case "dias": denominador = 1000 * 3600 * 24; break;
            case "minutos": denominador = 1000 * 60;
        }
        return Math.round((new Date(`${data}T${hora}-03:00`).getTime() - Date.now()) / denominador);
    }

    function formatarData(data, incluirDistancia = false) {
        let distanciaEmDias = calcularDistanciaData(data);
        let d = data.split("-");
        let mensagem = `${d[2]}/${d[1]}/${d[0]}`;
        if (incluirDistancia) {
            mensagem += "<br>";
            if (distanciaEmDias < 0) return mensagem + "Fechada";
            mensagem += distanciaEmDias > 0
                ? `(${calcularDistanciaData(data)} dias)`
                : "(Hoje)";
        }
        return mensagem;
    }

    function calcularDistanciaData(data) {
        const alvo = new Date(`${data}T00:00:00`);
        const hoje = new Date();
        hoje.setHours(0, 0, 0, 0);

        const diffDias = (alvo - hoje) / (1000 * 60 * 60 * 24);

        return Math.round(diffDias);
    }


    function atividadeAindaDisponivel(data, linha) {
        if ((new Date(data).getTime() - new Date().getTime()) / (1000 * 3600 * 24) <= 0) {
            linha.classList.add("atividade-fechada");
            return false;
        }
        return true;
    }

    function criarElement(tipo, valor = null, classe = null, id = null) {
        const el = document.createElement(tipo);
        if (valor) el.innerHTML = valor;
        if (classe) el.classList.add(classe);
        if (id) el.id = id;
        return el;
    }

    function preencherTabelaAtividades(atividade) {
        const linha = criarElement("tr", null, "atividade");
        const codigo = criarElement("td", atividade.id, "codigo");
        const nome = criarElement("td", atividade.nome, "nome");
        const tipo = criarElement("td", atividade.tipo, "tipo");
        const valor = criarElement("td", atividade.valor, "valor");
        const dataEnc = criarElement("td");
        const dataCri = criarElement("td");

        linha.classList.add("atividade");

        if (atividadeAindaDisponivel(atividade.dataEncerramento, linha)) {
            dataEnc.innerHTML = formatarData(atividade.dataEncerramento, true);
        }
        else {
            dataEnc.innerHTML = "Atividade encerrada";
        }
        atividade.dataCriacao ? dataCri.innerHTML = formatarData(atividade.dataCriacao) : dataCri.innerHTML = "Data desconhecida";

        linha.append(codigo, nome, tipo, valor, dataEnc, dataCri);
        corpoTable.append(linha);
    }

    function carregarAtividades() {
        fetch("/api/atividade/atividades")
            .then(response => {
                if (!response.ok) throw new Error("Erro ao carregar as atividades");
                return response.json();
            })
            .then(dados => {
                if (dados.length == 0) {
                    corpoTable.innerHTML = `
                        <tr id="msg-sem-atividade">
                            <td colspan="6"><p><i>Nenhuma atividade criada</i></p></td>
                        </tr>
                `;
                }
                else
                    corpoTable.innerHTML = "";
                dados.sort((a, b) => {
                    if (calcularDistanciaData(a.dataEncerramento) <= 0) return 1;
                    if (calcularDistanciaData(b.dataEncerramento) <= 0) return -1;
                    return a.id - b.id;
                });
                dados.forEach(atividade => {
                    preencherTabelaAtividades(atividade);
                    pesquisarPorAtividade();
                });
            })
            .catch(err => {
                console.log("Erro:", err);
            })
    }

    function normalizar(texto) {
        return texto
            .toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "");
    }

    function pesquisarPorAtividade() {
        const barraDePesquisa = document.getElementById("barra-de-pesquisa");

        barraDePesquisa.addEventListener("input", () => {

            const texto = normalizar(barraDePesquisa.value);
            const linhas = tabelaAtividades.querySelectorAll("tbody tr");

            linhas.forEach(linha => {
                const celulaNome = linha.querySelector(".nome");
                if (!celulaNome) return;

                const nomeAtvd = normalizar(celulaNome.textContent);

                linha.style.display = nomeAtvd.includes(texto) ? '' : 'none';
            });
        });
    }


    function initAtividades() {
        barraDePesquisa = document.getElementById("barra-de-pesquisa");;
        btnAddAtividade = document.getElementById("add-atividade");
        tabelaAtividades = document.getElementById("tabela-atividades");
        corpoTable = tabelaAtividades.querySelector("tbody")

        btnAddAtividade.addEventListener('click', () => {
            window.location.href = "/producao-atividades";
        });

        carregarAtividades();
    }

    initAtividades();
}

initSectionAtividadesProfessor();
document.querySelector("button[data-id='atividades']").addEventListener("click", initSectionAtividadesProfessor);