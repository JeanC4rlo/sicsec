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

    function irParaFazerAtividades(id) {
        fetch(`/api/atividade/${id}`)
            .then(r => r.json())
            .then(atividade => {
                localStorage.setItem("atividade", JSON.stringify(atividade));
                window.location.href = "/html/aluno/fazer-atividades.html";
            });
    }

    async function definirEstado(atividadeDTO) {
        const img = document.createElement("img");
        let caminho = "/images/icons/";
        let distanciaEmMinutos = calcularDistanciaData(
            atividadeDTO.dataEncerramento,
            atividadeDTO.horaEncerramento,
            "minutos"
        );

        if (distanciaEmMinutos <= 0) {
            img.src = caminho + "encerrado.svg";
            img.title = "Atividade fechada";
            return img;
        }

        try {
            const resp = await fetch(`/api/tentativa/atividade/${atividadeDTO.id}/num-tentativas`);
            if (!resp.ok) throw new Error("Erro ao carregar o número de tentativas");

            const numTentativasFeitas = await resp.json();

            if (numTentativasFeitas > 0) {
                img.src = caminho + "concluido.svg";
                img.title = "Você já fez essa atividade";
            }
            else if (distanciaEmMinutos > 60 * 24) {
                img.src = caminho + "atencao.svg";
                img.title = "Você ainda não fez essa atividade";
            }
            else {
                img.src = caminho + "alerta.svg";
                img.title = "Atividade ainda não feita e será fechada hoje";
            }
            return img;

        } catch (e) {
            console.error(e);
        }
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
            linha.addEventListener('click', () => irParaFazerAtividades(atividadeDTO.id));
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

        disciplina.innerHTML = "DISCIPLINA";

        professor.innerHTML = atividadeDTO.nomeProfessor;

        tentativasRestantes.innerHTML = atividadeDTO.numTentativasRestantes;

        valor.innerHTML = atividadeDTO.valor;

        if (atividadeDTO.nota != null)
            nota.innerHTML = atividadeDTO.nota;
        else {
            if (atividadeDTO.numTentativasRestantes == 0 && atividadeDTO.tipo != "Questionário")
                nota.innerHTML = "Sua resposta está sendo avaliada";
            else {
                nota.innerHTML = "Você ainda não fez a atividade";
            }
        }

        const imgEstado = await definirEstado(atividadeDTO);
        imgEstado.classList.add("img-estado");
        estado.append(imgEstado);

        linha.append(data, infoAtividade, disciplina, professor, tentativasRestantes, valor, nota, estado);
        corpoTable.append(linha);
    }

    function carregarAtividades() {
        fetch("/api/atividade/atividades-dto")
            .then(response => {
                if (!response.ok) throw new Error("Falha ao carregar as atividades");
                return response.json();
            })
            .then(async dados => {
                console.log(dados)
                const msg = document.getElementById("msg-sem-atividade");
                msg.classList.toggle("inativo", dados.length > 0);

                for (const atividadeDTO of dados) {
                    await atualizarTabelaAtividades(atividadeDTO);
                }
            })
            .catch(err => console.log("Erro:", err));
    }

    function initAtivdades() {
        carregarAtividades();
    }

    initAtivdades();
})();