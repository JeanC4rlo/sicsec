function initSectionAtividadesProfessor () {
    let barraDePesquisa;
    let btnAddAtividade;
    let tabelaAtividades;
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
        if ((new Date(data).getTime() - new Date().getTime()) / (1000 * 3600 * 24) <= 0) {
            linha.classList.add("encerrada");
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

        codigo.innerHTML = atividade.id;
        nome.innerHTML = atividade.nome;
        tipo.innerHTML = atividade.tipo;
        valor.innerHTML = atividade.valor;
        if (atividadeAindaDisponivel(atividade.dataEncerramento, linha)) {
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
                if (!response.ok) throw new Error("Erro ao carregar as atividades");
                return response.json();
            })
            .then(dados => {
                corpoTable.innerHTML = "";
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