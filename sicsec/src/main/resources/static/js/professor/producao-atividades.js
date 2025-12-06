function initSectionProducaoAtividades() {
    const state = {
        idxSection: -1,
        sequence: [],
        questoes: [],
        dadosForm: null,
        numQuestoes: null,
        dadosAtividade: {
            numTentativas: 1,
            tipoTimer: "none",
            numHoras: null,
            numMinutos: null,
            enunciado: ""
        }
    }

    const htmlDOM = {
        btnProximo: null,
        btnAnterior: null,
        btnSair: null,
        btnEnviar: null,
        btnEnviarArquivo: null,
        section: null
    }

    let inputDeArquvios;
    let handlerProximoAtual = null;

    const sequenciasAcoes = {
        questionario: [
            montarTelaQuestoes,
            () => montarTelaDuracaoETentativas("duracao-e-tentativas"),
            montarConfirmacao
        ],
        redacao: [
            montarTelaElaboracaoEnunciado,
            () => montarTelaDuracaoETentativas("duracao"),
            montarConfirmacao
        ],
        envioArquivo: [
            montarTelaElaboracaoEnunciado,
            montarConfirmacao
        ]
    };

    const LIMITES = {
        numAlternativas: 5,
        tamanhoTextosPequenos: 252,
        tamanhoTextosGrandes: 2000
    }

    const tipos = {
        questionario: "Questionário",
        redacao: "Redação",
        envioArquivo: "Envio de Arquivo"
    };

    function initProdAtividade() {
        htmlDOM.section = document.querySelector("section");
        htmlDOM.btnSair = document.getElementById("btn-sair");
        htmlDOM.btnAnterior = document.getElementById("btn-anterior");
        htmlDOM.btnProximo = document.getElementById("btn-proximo");
        htmlDOM.btnEnviar = document.getElementById("btn-enviar");

        htmlDOM.btnSair.addEventListener("click", confirmarSaidaDaPag);
        montarPrimeiraTela();
        htmlDOM.btnAnterior.addEventListener("click", () => {
            if (handlerProximoAtual) {
                htmlDOM.btnProximo.removeEventListener("click", handlerProximoAtual);
                handlerProximoAtual = null;
            }
            retroceder();
        });
        htmlDOM.btnEnviar.addEventListener("click", enviar);
    }

    function destacarCampo(campo) {
        campo.classList.add("campo-obrigatorio");
        setTimeout(() => campo.classList.remove("campo-obrigatorio"), 2000);
        campo.focus();
    }

    function validarCampo(campo) {
        if (!campo.value.trim()) {
            destacarCampo(campo);
            return false;
        }
        return true;
    }

    function validarContainer(container) {
        const campos = container.querySelectorAll("input, textarea, select");
        for (const campo of campos) {
            if (!campo.value.trim() && !campo.classList.contains("texto-questao")) {
                destacarCampo(campo);
                return false;
            }
        }
        return true;
    }

    function validarLista(container, config) {
        const itens = Array.from(container.children);
        for (const item of itens) {
            for (const sel of config.selectors) {
                const campos = container.querySelectorAll(`:scope > ${sel}`);
                for (const campo of campos) {
                    if (!campo.value.trim()) {
                        validarCampo(campo);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    function validarData(data) {
        const [ano, mes, dia] = data.split("-").map(Number);
        const dataObj = new Date(ano, mes - 1, dia);
        return dataObj.getTime() > Date.now();
    }

    function validarTempo() {
        const listaInputs = document.querySelectorAll("#duracao-e-tentativas > input");
        for (let input of listaInputs) {
            if (!input.value.trim()) {
                validarCampo(input);
                return false;
            }
        }
        return true;
    }

    function avancar() {
        state.idxSection = Math.min(state.idxSection + 1, state.sequence.length - 1);
        state.sequence[state.idxSection]();
    }

    function retroceder() {
        state.idxSection = Math.max(-1, state.idxSection - 1);
        if (state.idxSection == -1) {
            montarPrimeiraTela();
            return;
        }
        state.sequence[state.idxSection]();
    }

    function enviarArquivo() {
        inputDeArquvios = document.createElement("input");
        inputDeArquvios.type = "file";
        inputDeArquvios.style.display = "none";
        inputDeArquvios.id = "fileInput";
        inputDeArquvios.multiple = true;
        inputDeArquvios.accept = "application/zip,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain";
        htmlDOM.section.appendChild(inputDeArquvios);

        inputDeArquvios.click()

        const listaArquivos = document.getElementById("lista-arquivos");

        inputDeArquvios.addEventListener("change", () => {
            listaArquivos.innerHTML = "";
            for (let i = 0; i < inputDeArquvios.files.length; i++) {
                let li = document.createElement("li");
                li.textContent = inputDeArquvios.files[i].name;
                listaArquivos.appendChild(li);
            }
        });
    }

    function adicionarQuestao() {
        state.numQuestoes++;

        const corpo = document.createElement("div");
        corpo.classList.add("questao", "ativo");

        const numDaQuestao = document.createElement("p");
        numDaQuestao.innerHTML = `Questão ${state.numQuestoes}`;
        numDaQuestao.classList.add("num-questao");

        const txtDaQuestao = document.createElement("textarea");
        txtDaQuestao.placeholder = "Texto da questão";
        txtDaQuestao.maxLength = LIMITES.tamanhoTextosGrandes;
        txtDaQuestao.classList.add("texto-questao");
        expandirTextarea(txtDaQuestao);

        const enunciadoQuestao = document.createElement("textarea");
        enunciadoQuestao.placeholder = "Enunciado da questão";
        enunciadoQuestao.style.marginBottom = "0.5rem";
        expandirTextarea(enunciadoQuestao);

        const listaAlternativas = document.createElement("div");
        listaAlternativas.classList.add("lista-alternativas");

        const btnAddAlternativa = document.createElement("button");
        btnAddAlternativa.textContent = "Adicionar alternativa";
        btnAddAlternativa.classList.add("btn-add-alternativa")
        btnAddAlternativa.addEventListener("click", () => adicionarAlternativa(listaAlternativas));

        const btnRmvQuestao = document.createElement("button");
        btnRmvQuestao.textContent = "Excluir questão";
        btnRmvQuestao.classList.add("btn-excluir-questao");
        btnRmvQuestao.addEventListener("click", () => removerQuestao(corpo));

        adicionarAlternativa(listaAlternativas);

        corpo.append(numDaQuestao, txtDaQuestao, enunciadoQuestao, listaAlternativas, btnAddAlternativa, btnRmvQuestao);
        document.querySelector("#lista-questoes").appendChild(corpo);

        return corpo;
    }

    function removerQuestao(corpo) {
        if (state.numQuestoes < 2) {
            alert("Um questionário deve ter pelo menos uma questões");
            return;
        }
        corpo.remove();
        const listaNumDasQuestoes = document.querySelectorAll("#lista-questoes .num-questao");
        listaNumDasQuestoes.forEach((num, index) => num.innerHTML = `Questão ${index + 1}`);
        state.numQuestoes--;
    }

    function adicionarAlternativa(listaAlternativas, alt = null) {
        const numAlternativas = listaAlternativas.children.length;
        if (numAlternativas >= LIMITES.numAlternativas) {
            alert("Uma questão pode ter no máximo 6 alternativas");
            return;
        }

        const corpo = document.createElement("div");
        corpo.classList.add("alternativa");

        const texto = document.createElement("textarea");
        texto.placeholder = "Texto da alternativa";
        expandirTextarea(texto);

        const ehCorreta = document.createElement("select");
        ehCorreta.add(new Option("Errada", "false"));
        ehCorreta.add(new Option("Correta", "true"));

        const btnRmvAlternativa = document.createElement("button");
        btnRmvAlternativa.textContent = "Remover alternativa";
        btnRmvAlternativa.classList.add("btn-rmv-alternativa")
        btnRmvAlternativa.addEventListener("click", () => corpo.remove());

        corpo.append(texto, ehCorreta, btnRmvAlternativa);
        listaAlternativas.appendChild(corpo);

        if (alt != null) {
            texto.value = alt.texto;
            ehCorreta.value = alt.correta;
        }
    }

    function expandirTextarea(textarea) {
        textarea.addEventListener("input", () => {
            textarea.style.height = "auto";
            textarea.style.height = textarea.scrollHeight + "px";
        });
    }

    function desabilitarCampoTempoTimer() {
        const numHoras = document.querySelector("#num-horas");
        const numMinutos = document.querySelector("#num-minutos");
        const tipoTimer = document.querySelector("#tipo-timer");
        if (tipoTimer.value == "none") {
            numHoras.disabled = true;
            numMinutos.disabled = true;
        }
        tipoTimer.addEventListener("input", () => {
            numHoras.disabled = tipoTimer.value === "none";
            numMinutos.disabled = tipoTimer.value === "none";
        });
    }

    function enviar() {
        const dados = {
            nome: state.dadosForm.get("nome"),
            tipo: tipos[state.dadosForm.get("tipo")],
            valor: state.dadosForm.get("valor"),
            dataEncerramento: state.dadosForm.get("dataEncerramento"),
            horaEncerramento: state.dadosForm.get("horaEncerramento"),
            enunciado: null,
            questoes: null,
            tentativas: "1",
            tempoDeDuracao: null,
            tipoTimer: "none"
        };

        if (state.dadosForm.get("tipo") !== "envioArquivo") {
            dados.tipoTimer = state.dadosAtividade.tipoTimer;
            if (dados.tipoTimer !== "none") {
                const numHoras = state.dadosAtividade.numHoras;
                const numMinutos = state.dadosAtividade.numMinutos;
                dados.tempoDeDuracao = JSON.stringify({ numHoras, numMinutos });
            }
        }

        if (state.dadosForm.get("tipo") === "questionario") {
            let idxCorreta;
            const questoesCorrigidas = state.questoes.map(questao => {
                const alternativas = questao.alternativas.map((alt, idx) => {
                    if (alt.correta) idxCorreta = idx;
                    return { texto: alt.texto };
                });

                return {
                    texto: questao.texto,
                    enunciado: questao.enunciado,
                    alternativas: alternativas,
                    idxCorreta: idxCorreta
                };
            });

            dados.questoes = JSON.stringify(questoesCorrigidas);

            dados.tentativas = state.dadosAtividade.numTentativas;
        } else {
            dados.enunciado = state.dadosAtividade.enunciado;
        }


        const formData = new FormData();
        formData.append("atividade", new Blob([JSON.stringify(dados)], { type: "application/json" }));

        if (dados.tipo != "questionario" && inputDeArquvios && inputDeArquvios.files.length > 0) {
            for (const file of inputDeArquvios.files) {
                if (file.size > 5 * 1024 * 1024) {
                    alert(`Arquivo muito grande: ${file.name}`);
                    return;
                }
                const extensoesPermitidas = [".txt", ".pdf", ".docx", ".zip", ".jpg", ".png"];
                const nome = file.name.toLowerCase();
                if (!extensoesPermitidas.some(ext => nome.endsWith(ext))) {
                    alert(`Tipo de arquivo não permitido: ${file.name}`);
                    return;
                }
            }
            for (const file of inputDeArquvios.files) {
                formData.append("arquivos", file);
            }
        }

        fetch("/api/atividade/salvar", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) throw new Error("Erro ao enviar os dados");
                return response.json();
            })
            .then(data => {
                window.location.href = "/home";
            })
            .catch(err => console.error("Falha no envio:", err));
    }

    function salvarQuestoes() {
        const listaQuestoes = document.querySelectorAll("#lista-questoes .questao");
        state.questoes = Array.from(listaQuestoes).map(questao => {
            const texto = questao.querySelector("textarea:nth-of-type(1)")?.value || "";
            const enunciado = questao.querySelector("textarea:nth-of-type(2)")?.value || "";

            const alternativas = Array.from(
                questao.querySelectorAll(".lista-alternativas .alternativa")
            ).map(alt => ({
                texto: alt.querySelector("textarea")?.value || "",
                correta: alt.querySelector("select")?.value === "true"
            }));

            return { texto, enunciado, alternativas };
        });

        return state.questoes;
    }

    function validarQuestoes() {
        const listaQuestoes = document.querySelectorAll("#lista-questoes .questao");
        for (let i = 0; i < listaQuestoes.length; i++) {
            const questao = listaQuestoes[i];
            const alternativas = questao.querySelectorAll(".lista-alternativas .alternativa");

            if (alternativas.length < 2) {
                alert(`A questão ${i + 1} está com menos que duas alternativas`);
                return false;
            }

            let contador = 0;
            alternativas.forEach(alt => {
                if (alt.querySelector("select")?.value === "true") {
                    contador++;
                }
            });

            if (contador !== 1) {
                if (contador > 1)
                    alert(`A questão ${i + 1} está com mais de uma alternativa marcada como correta`);
                else
                    alert(`A questão ${i + 1} está com nenhuma alternativa marcada como correta`);

                return false;
            }
        }
        return true;
    }

    function criarHandlerProximo(validacao) {
        const handler = () => {
            const valido = validacao();
            if (!valido) return;
            avancar();
            htmlDOM.btnProximo.removeEventListener("click", handler);
        };
        handlerProximoAtual = handler;
        return handler;
    }

    function montarPrimeiraTela() {
        htmlDOM.section.innerHTML = `
        <form id="form-principal" action="atividades.html" method="post">
        <label>Nome da atividade:</label>
        <input type="text" name="nome"><br>
        <label>Tipo:</label>
        <select name="tipo">
            <option value="questionario">Questionário</option>
            <option value="redacao">Redação</option>
            <option value="envioArquivo">Envio de Arquivo</option>
        </select><br>
        <label>Valor (pontos):</label>
        <input type="number" name="valor" min="1"><br>
        <label>Data de encerramento:</label>
        <input name="dataEncerramento" type="date"><br>
        <label>Horário do encerramento:</label>
        <input name="horaEncerramento" type="time">
    </form>
    `

        if (state.dadosForm != null) restaurarPrimeiraTela();

        const validacao = () => {
            let form = document.querySelector("form");
            if (state.idxSection == -1) {
                for (let campo of form.elements) {
                    if (!validarCampo(campo)) return false;
                }
                if (!validarData(form.dataEncerramento.value)) {
                    alert("A data inserida é inválida");
                    destacarCampo(form.dataEncerramento);
                    return false;
                }
                state.sequence = sequenciasAcoes[form.tipo.value];
                state.dadosForm = new FormData(form);
                return true;
            }
        };

        const handlerProximo = criarHandlerProximo(validacao);
        htmlDOM.btnProximo.addEventListener("click", handlerProximo);
    }

    function restaurarPrimeiraTela() {
        let form = document.querySelector("form");
        form.elements["nome"].value = state.dadosForm.get("nome");
        form.elements["tipo"].value = state.dadosForm.get("tipo");
        form.elements["valor"].value = state.dadosForm.get("valor");
        form.elements["dataEncerramento"].value = state.dadosForm.get("dataEncerramento");
        form.elements["horaEncerramento"].value = state.dadosForm.get("horaEncerramento");
    }

    function montarTelaQuestoes() {
        htmlDOM.section.innerHTML = `
        <div id="questoes">
            <h3>Questões do questionário</h3>
            <button id="btn-add-questao">Adicionar questão</button>
            <div id="lista-questoes"></div>
        </div>
    `
        if (state.questoes && state.questoes.length != 0) restaurarTelaQuestoes();

        const btnAddQuestao = document.getElementById("btn-add-questao");
        btnAddQuestao.addEventListener("click", adicionarQuestao);

        const validacao = () => {
            if (state.numQuestoes < 1) {
                alert("Um questionário deve ter pelo menos uma questões");
                return false;
            }
            if (!validarQuestoes()) return false;
            if (!validarContainer(document.querySelector("#lista-questoes"))) return false;
            state.questoes = salvarQuestoes();
            return true;
        };

        const handlerProximo = criarHandlerProximo(validacao);
        htmlDOM.btnProximo.addEventListener("click", handlerProximo);
    }

    function restaurarTelaQuestoes() {

        // limpa o container antes de recriar
        const container = document.querySelector("#lista-questoes");
        container.innerHTML = "";

        state.numQuestoes = 0;

        // garante que questoesArray existe e é um array
        if (!Array.isArray(state.questoes)) return;

        // recria cada questão
        state.questoes.forEach(q => {
            adicionarQuestaoComDados(q);
        });
    }

    function adicionarQuestaoComDados(dados) {
        const div = adicionarQuestao();

        div.querySelector("textarea:nth-of-type(1)").value = dados.texto;
        div.querySelector("textarea:nth-of-type(2)").value = dados.enunciado;

        const cont = div.querySelector(".lista-alternativas");
        cont.innerHTML = "";
        dados.alternativas.forEach(alt => {
            adicionarAlternativa(cont, alt);
        });
    }

    function montarTelaDuracaoETentativas(tipo = "duracao-e-tentativas") {
        if (tipo == "duracao-e-tentativas") {
            htmlDOM.section.innerHTML = `
        <div id="duracao-e-tentativas">
            <h3>Numéro de tentativas, duração e tipo de timer</h3>
            <label for="">Número de tentativas:</label>
            <input id="num-tentativas" type="number" min="1" value="1"><br>
            <label for="">Tipo de timer</label>
            <div class="tooltip">?
                <span class="tooltiptext">Contínuo: assim que começa, não pode ser parado. Sugerido para provas.<br><br>Interrompível: é interrompido quando usuário sai da página. Sugerido para atividades simples.</span>
            </div>
            <select name="tipo-timer" id="tipo-timer">
                <option value="none" selected>Sem timer</option>
                <option value="continuo">Contínuo</option>
                <option value="interrompivel">Interrompível</option>
            </select><br>
            <label>Duração:</label>
            <input id="num-horas" type="number" min="0" max="24" placeholder="Horas">
            <input id="num-minutos" type="number" min="0" max="59" placeholder="Minutos"><br>
        </div>
    `
        }
        if (tipo == "duracao") {
            htmlDOM.section.innerHTML = `
        <div id="duracao-e-tentativas">
            <h3>Duração e tipo de timer</h3>
            <label for="">Tipo de timer</label>
            <div class="tooltip">?
                <span class="tooltiptext">Contínuo: assim que começa, não pode ser parado. Sugerido para provas.<br><br>Interrompível: é interrompido quando usuário sai da página. Sugerido para atividades simples.</span>
            </div>
            <select name="tipo-timer" id="tipo-timer">
                <option value="none" selected>Sem timer</option>
                <option value="continuo">Contínuo</option>
                <option value="interrompivel">Interrompível</option>
            </select><br>
            <label>Duração:</label>
            <input id="num-horas" type="number" id="horas" min="0" max="24" placeholder="Horas">
            <input id="num-minutos" type="number" id="minutos" min="0" max="59" placeholder="Minutos"><br>
        </div>
    `
        }

        restaurarTelaDuracaoETentativas();
        desabilitarCampoTempoTimer();

        const validacao = function () {
            const tipoTimer = document.querySelector("#tipo-timer");
            let valido;
            if (tipoTimer.value == "none") valido = true;
            else valido = validarLista(document.getElementById("duracao-e-tentativas"), { selectors: ["input"] });
            if (valido) {
                if (document.getElementById("num-tentativas")) state.dadosAtividade.numTentativas = document.getElementById("num-tentativas").value;
                state.dadosAtividade.tipoTimer = document.getElementById("tipo-timer").value;
                state.dadosAtividade.numHoras = document.getElementById("num-horas").value;
                state.dadosAtividade.numMinutos = document.getElementById("num-minutos").value;
                return true;
            }
            return false;
        };

        const handlerProximo = criarHandlerProximo(validacao);
        htmlDOM.btnProximo.addEventListener("click", handlerProximo);
    }

    function restaurarTelaDuracaoETentativas() {
        if (document.getElementById("num-tentativas")) {
            if (state.dadosAtividade.numTentativas == null || state.dadosAtividade.numTentativas === "")
                document.getElementById("num-tentativas").value = "1";
            else
                document.getElementById("num-tentativas").value = state.dadosAtividade.numTentativas;
        }

        if (state.dadosAtividade.tipoTimer == null || state.dadosAtividade.tipoTimer === "")
            document.getElementById("tipo-timer").value = "none";
        else
            document.getElementById("tipo-timer").value = state.dadosAtividade.tipoTimer;


        const horas = document.getElementById("num-horas");
        if (horas && state.dadosAtividade.numHoras !== undefined) {
            horas.value = state.dadosAtividade.numHoras;
        }

        const minutos = document.getElementById("num-minutos");
        if (minutos && state.dadosAtividade.numMinutos !== undefined) {
            minutos.value = state.dadosAtividade.numMinutos;
        }
    }

    function montarTelaElaboracaoEnunciado() {
        htmlDOM.section.innerHTML = `
        <div id="elaboracao">
            <label>Insira o enunciado:</label>
            <input id="input-enunciado" type="textarea">
            <button onclick="enviarArquivo()" id="btn-enviar-arquivo">Inserir arquivo(s) de consulta?</button>
            <ul id="lista-arquivos"></ul>
        </div>
    `

        restaurarTelaElaboracaoEnunciado();

        const validacao = function () {
            console.log(document.getElementById("elaboracao"));
            if (!validarContainer(document.getElementById("elaboracao"))) {
                return false;
            }
            state.dadosAtividade.enunciado = document.getElementById("input-enunciado").value;
            return true;
        };

        const handlerProximo = criarHandlerProximo(validacao);
        htmlDOM.btnProximo.addEventListener("click", handlerProximo);
    }

    function restaurarTelaElaboracaoEnunciado() {
        if (inputDeArquvios == null || inputDeArquvios.files.length == 0) return;

        const enunciado = document.getElementById("input-enunciado");
        enunciado.value = state.dadosAtividade.enunciado;

        const listaArquivos = document.getElementById("lista-arquivos");
        Array.from(inputDeArquvios.files).forEach(file => {
            const li = document.createElement("li");
            li.textContent = file.name;
            listaArquivos.appendChild(li);
        });
    }

    function montarConfirmacao() {
        htmlDOM.section.innerHTML = `
        <div id="confirmacao">
            <p id="nome">Nome: </p>
            <p id="tipo">Tipo: </p>
            <p id="valor">Valor: </p>
            <p id="dataEncerramento">Data de encerramento: </p>
            <p id="horaEncerramento">Hora de encerramento: </p>
    `
        htmlDOM.btnEnviar.classList.remove("inativo");
        htmlDOM.btnAnterior.addEventListener("click", () => {
            htmlDOM.btnEnviar.classList.add("inativo");
        }, { once: true });

        const nome = document.getElementById("nome");
        const tipo = document.getElementById("tipo");
        const valor = document.getElementById("valor");
        const dataEncerramento = document.getElementById("dataEncerramento");
        const horaEncerramento = document.getElementById("horaEncerramento");

        nome.innerHTML += state.dadosForm.get("nome");
        tipo.innerHTML += tipos[state.dadosForm.get("tipo")];
        valor.innerHTML += state.dadosForm.get("valor");
        dataEncerramento.innerHTML += state.dadosForm.get("dataEncerramento");
        horaEncerramento.innerHTML += state.dadosForm.get("horaEncerramento");

        if (state.dadosForm.get("tipo") == "questionario") {
            htmlDOM.section.innerHTML += `<p id="numQuestoes">Número de questões: ${state.numQuestoes}</p>`
        } else {
            htmlDOM.section.innerHTML += `<p id="enunciado">Enunciado: ${state.dadosAtividade.enunciado}</p>`
        }
        htmlDOM.section.innerHTML += `</div>`
    }

    function confirmarSaidaDaPag() {
        if (confirm("Tem certeza que deseja sair da página? Os dados da atividade serão perdidos?")) {
            window.location.href = "/home";
        }
    }

    window.onload = initProdAtividade;
}

initSectionProducaoAtividades();