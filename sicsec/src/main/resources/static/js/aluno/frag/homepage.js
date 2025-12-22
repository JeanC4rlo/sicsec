(function () {
    const noticias = [
        { detalhe: '15/10/2025 - PORTUGUÊS', texto: 'Livro Memórias Póstumas de Brás Cubas' },
        { detalhe: '16/10/2025 - BIOLOGIA', texto: 'Aula no auditório 202 P19' },
        { detalhe: '17/10/2025 - QUÍMICA', texto: 'Apresentação de Trabalhos' }
    ];

    let indiceAtual = 0;
    let intervaloSlideshow;
    const tempoIntervalo = 5000;

    function atualizarNoticia(indice) {
        const noticia = noticias[indice];
        document.getElementById('detalheNoticia').textContent = noticia.detalhe;
        document.getElementById('textoNoticia').textContent = noticia.texto;
    }

    function pararSlideshow() {
        clearInterval(intervaloSlideshow);
        const pararBtn = document.getElementById('btnParar');
        pararBtn.textContent = 'INICIAR';
        pararBtn.classList.add('ativo');
    }

    function avancarIndiceAutomatico() {
        indiceAtual = (indiceAtual + 1) % noticias.length;
        atualizarNoticia(indiceAtual);
    }

    function iniciarSlideshow() {
        pararSlideshow();
        intervaloSlideshow = setInterval(avancarIndiceAutomatico, tempoIntervalo);
        const pararBtn = document.getElementById('btnParar');
        pararBtn.textContent = 'PARAR';
        pararBtn.classList.remove('ativo');
    }

    function navegarManual(direcao) {
        pararSlideshow();
        indiceAtual += direcao;

        if (indiceAtual >= noticias.length) indiceAtual = 0;
        else if (indiceAtual < 0) indiceAtual = noticias.length - 1;

        atualizarNoticia(indiceAtual);
    }

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

    async function atualizarAtividades(atividadeDTO) {
        if (calcularDistanciaData(atividadeDTO.dataEncerramento) <= -3) {
            return;
        }

        const corpoTable = document.querySelector(".minhas-atividades tbody");
        const linha = document.createElement("tr");

        const data = document.createElement("td");
        data.classList.add("data-col");

        const infoAtividade = document.createElement("td");
        infoAtividade.classList.add("info-atividade");

        const estado = document.createElement("td");
        estado.classList.add("estado");

        if (calcularDistanciaData(atividadeDTO.dataEncerramento, atividadeDTO.horaEncerramento, "minutos") > 0) {
            linha.addEventListener('click', () => irParaFazerAtividades(atividadeDTO.id, atividadeDTO.disciplina.id));
        }
        else
            linha.classList.add("atividade-fechada");

        data.innerHTML = formatarDataEHora(
            atividadeDTO.dataEncerramento,
            atividadeDTO.horaEncerramento
        );

        const disciplina = document.createElement("span");
        disciplina.classList.add("disciplina");
        disciplina.innerHTML = atividadeDTO.disciplina.nome;

        const avaliacao = document.createElement("span");
        avaliacao.classList.add("avaliacao");
        avaliacao.innerHTML = `${atividadeDTO.nome}<br>${atividadeDTO.tipo}: ${atividadeDTO.valor} ponto`;
        if (atividadeDTO.valor > 1) avaliacao.innerHTML += "s";

        const imgEstado = await getImgEstado(atividadeDTO);
        imgEstado.classList.add("img-estado");
        estado.append(imgEstado);


        infoAtividade.append(disciplina, avaliacao);
        linha.append(data, infoAtividade, estado);
        corpoTable.append(linha);
    }

    function carregarAtividades() {
        fetch("/api/atividade/home-atividades")
            .then(response => {
                if (!response.ok) throw new Error("Falha ao carregar as atividades");
                return response.json();
            })
            .then(async dados => {
                const msg = document.getElementById("msg-sem-atividade-homepage");
                msg.classList.toggle("inativo", dados.length > 0);

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
                    await atualizarAtividades(atividadeDTO);
                }
            })
            .catch(err => console.log("Erro:", err));
    }

    function initHomepage() {
        document.getElementById('prevNoticia').addEventListener('click', () => navegarManual(-1));
        document.getElementById('nextNoticia').addEventListener('click', () => navegarManual(1));
        document.getElementById('btnParar').addEventListener('click', () => {
            const btn = document.getElementById('btnParar');
            btn.textContent === 'PARAR' ? pararSlideshow() : iniciarSlideshow();
        });

        atualizarNoticia(indiceAtual);
        iniciarSlideshow();
        carregarAtividades();
    }

    initHomepage();
})();
