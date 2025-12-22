(function () {
    function calcularDistanciaData(data, hora = "00:00", tipo = "dias") {
        let denominador = 1;
        switch (tipo) {
            case "dias": denominador = 1000 * 3600 * 24; break;
            case "minutos": denominador = 1000 * 60;
        }
        return Math.round((new Date(`${data}T${hora}-03:00`).getTime() - Date.now()) / denominador);
    }

    function formatarDataEHora(data, hora) {
        let distanciaEmMinutos = calcularDistanciaData(data, hora, "minutos");
        let d = data.split("-");
        let mensagem = `${d[2]}/${d[1]}/${d[0]} ${hora}<br>`;
        if (distanciaEmMinutos < 0) return mensagem + "Fechada";
        mensagem += distanciaEmMinutos > 60 * 24
            ? `(${calcularDistanciaData(data)} dias)`
            : "(Hoje)";
        return mensagem;
    }

    function irParaFazerAtividades(atividadeId, disciplinaId) {
        localStorage.setItem("atividadeId", atividadeId);
        localStorage.setItem("disciplinaId", disciplinaId)
        window.location.href = "/html/aluno/fazer-atividades.html";
    }

    async function definirEstado(atividadeDTO) {
        let distanciaEmMinutos = calcularDistanciaData(
            atividadeDTO.dataEncerramento,
            atividadeDTO.horaEncerramento,
            "minutos"
        );

        if (distanciaEmMinutos <= 0) {
            return "fechada";
        }

        try {
            const resp = await fetch(`/api/tentativa/atividade/${atividadeDTO.id}/num-tentativas`);
            if (!resp.ok) throw new Error("Erro ao carregar o número de tentativas");

            const numTentativasFeitas = await resp.json();

            if (numTentativasFeitas > 0) {
                return "concluida";
            }
            else if (distanciaEmMinutos > 60 * 24) {
                return "aberta";
            }
            else {
                return "fechando";
            }

        } catch (e) {
            console.error(e);
        }
    }

    async function getImgEstado(atividadeDTO) {
        const estado = await definirEstado(atividadeDTO);
        console.log(estado);
        const img = document.createElement("img");
        let caminho = "/images/icons/";
        switch (estado) {
            case "concluida":
                img.src = caminho + "concluido.svg";
                img.title = "Você já fez essa atividade";
                break;
            case "aberta":
                img.src = caminho + "atencao.svg";
                img.title = "Você ainda não fez essa atividade";
                break;
            case "fechando":
                img.src = caminho + "alerta.svg";
                img.title = "Atividade ainda não feita e será fechada hoje";
                break;
            default:
                img.src = caminho + "encerrado.svg";
                img.title = "Atividade fechada";
                break;
        }
        return img;
    }

    function criarDOMElement(tipo, classe = null, id = null) {
        const el = document.createElement(tipo);
        if (classe) el.classList.add(classe)
        if (id) el.id = id;
        return el;
    }

    async function atualizarTabelaAtividades(atividadeDTO) {
        if (calcularDistanciaData(atividadeDTO.dataEncerramento) <= -3) {
            return;
        }

        const corpoTable = document.querySelector("#tabela-atividades tbody");
        const linha = criarDOMElement("tr");
        const data = criarDOMElement("td", "data-col");
        const infoAtividade = criarDOMElement("td", "info-atividade");
        const disciplina = criarDOMElement("td", "disciplina");
        const professor = criarDOMElement("td", "professor");
        const tentativasRestantes = criarDOMElement("td", "tentativasRestantes");
        const valor = criarDOMElement("td", "valor");
        const nota = criarDOMElement("td", "nota");
        const estado = criarDOMElement("td", "estado");

        if (calcularDistanciaData(atividadeDTO.dataEncerramento, atividadeDTO.horaEncerramento, "minutos") > 0) {
            linha.addEventListener('click', () => irParaFazerAtividades(atividadeDTO.id, atividadeDTO.disciplina.id));
        }
        else
            linha.classList.add("atividade-fechada");

        data.innerHTML = formatarDataEHora(
            atividadeDTO.dataEncerramento,
            atividadeDTO.horaEncerramento
        );

        const nomeAtividade = criarDOMElement("span", "nome-atividade");
        const tipo = criarDOMElement("span", "tipo");
        nomeAtividade.innerHTML = atividadeDTO.nome;
        tipo.innerHTML = atividadeDTO.tipo;
        infoAtividade.append(nomeAtividade, tipo);

        disciplina.innerHTML = atividadeDTO.disciplina.nome;

        professor.innerHTML = atividadeDTO.nomeProfessor;

        tentativasRestantes.innerHTML = atividadeDTO.numTentativasRestantes;

        valor.innerHTML = atividadeDTO.valor;

        if (atividadeDTO.nota != null)
            nota.innerHTML = atividadeDTO.nota;
        else {
            if (atividadeDTO.numTentativasRestantes == 0 && atividadeDTO.tipo != "Questionário")
                nota.innerHTML = "Sua resposta está sendo avaliada";
            else {
                nota.innerHTML = "Atividade ainda não feita";
            }
        }

        const imgEstado = await getImgEstado(atividadeDTO);
        imgEstado.classList.add("img-estado");
        estado.append(imgEstado);

        linha.append(data, infoAtividade, disciplina, professor, tentativasRestantes, valor, nota, estado);
        corpoTable.append(linha);
    }

    function normalizar(texto) {
        return texto
            .toLowerCase()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "");
    }

    function pesquisarPorAtividade() {
        const barraDePesquisa = document.getElementById("barra-de-pesquisa-atividades");
        const tabela = document.getElementById("tabela-atividades");

        barraDePesquisa.addEventListener("input", () => {

            const texto = normalizar(barraDePesquisa.value);
            const linhas = tabela.querySelectorAll("tbody tr");

            linhas.forEach(linha => {
                const celulaNome = linha.querySelector(".nome-atividade");
                if (!celulaNome) return;

                const nomeAtvd = normalizar(celulaNome.textContent);

                linha.style.display = nomeAtvd.includes(texto) ? '' : 'none';
            });
        });
    }

    async function carregarAtividades() {
        try {
            const response = await fetch("/api/atividade/atividades-dto");

            if (!response.ok) {
                throw new Error("Falha ao carregar as atividades");
            }

            const dados = await response.json();

            const msg = document.getElementById("msg-sem-atividade");
            msg.classList.toggle("inativo", dados.length > 0);

            console.log(dados);

            const ORDEM_ESTADO = {
                fechando: 0,
                aberta: 1,
                concluida: 2,
                fechada: 3
            };
            await Promise.all(
                dados.map(async d => {
                    d._estado = await definirEstado(d);
                })
            );
            dados.sort((a, b) => {
                return ORDEM_ESTADO[a._estado] - ORDEM_ESTADO[b._estado];
            });
            for (const atividadeDTO of dados) {
                await atualizarTabelaAtividades(atividadeDTO);
            }

        } catch (err) {
            console.log("Erro:", err);
        }
    }


    function initAtivdades() {
        pesquisarPorAtividade();
        carregarAtividades();
    }

    initAtivdades();
})();