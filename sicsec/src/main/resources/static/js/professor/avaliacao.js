let dadosDesempenho;
const divConteudo = document.getElementById("conteudo");

function carregarResposta() {
    const params = new URLSearchParams(window.location.search);
    const desempenhoId = params.get("id");

    fetch(`/api/desempenho/${desempenhoId}`)
        .then(resp => {
            if (!resp.ok) throw new Error("Erro ao carregar a tentativa");
            return resp.json();
        })
        .then(dados => {
            dadosDesempenho = dados;
            console.log(dadosDesempenho);
            telaAvaliacao();
        })
        .catch(err => {
            console.error(err);
        })
}

function telaAvaliacao() {
    const nomeAluno = document.getElementById("nome");
    const turmaAluno = document.getElementById("turma");
    const inputNota = document.createElement("input");
    const btnEnviarNota = document.createElement("button");

    nomeAluno.innerHTML = dadosDesempenho.nomeAluno;
    turmaAluno.innerHTML = "INF-2A";

    inputNota.id = "input-nota";
    inputNota.type = "text";
    inputNota.placeholder = "Insirir nota";
    inputNota.min = 0;
    inputNota.max = dadosDesempenho.valorAtividade;
    inputNota.addEventListener("input", () => validaInputNota(inputNota));

    btnEnviarNota.textContent = "Submeter nota";
    btnEnviarNota.id = "btn-enviar";
    btnEnviarNota.addEventListener("click", enviarNota);

    switch (dadosDesempenho.tipoAtividade) {
        case "Redação":
            addRedacao(inputNota, btnEnviarNota);
            break;

        case "Envio de Arquivo":
            addArquivo(inputNota, btnEnviarNota);
            break;
    }

    document.getElementById("btn-sair").addEventListener("click", confirmarSaidaDaPag);
}

function addRedacao(inpNota, btnEnviar) {
    const conteudoAluno = document.querySelector(".conteudo-aluno");

    const redacao = document.createElement("p");
    redacao.classList.add("texto-redacao");
    conteudoAluno.append(redacao);

    redacao.innerHTML = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    divConteudo.append(conteudoAluno, inpNota, btnEnviar);
}

function addArquivo(inpNota, btnEnviar) {
    const conteudoAluno = document.querySelector(".conteudo-aluno");
    const inputArquivo = document.createElement("input");
    const divPreview = document.createElement("div");
    const btnBaixarArquivo = document.createElement("button");

    conteudoAluno.append(divPreview);

    inputArquivo.type = "file";
    carregarPreview(dadosDesempenho.arquivoId, dadosDesempenho.respostaId, divPreview);
    btnBaixarArquivo.textContent = "Baixar arquivo";
    btnBaixarArquivo.id = "btn-baixar";
    btnBaixarArquivo.addEventListener("click", () => downloadArquivo(dadosDesempenho.arquivoId));
    divConteudo.append(btnBaixarArquivo, inpNota, btnEnviar);
}

async function carregarPreview(arquivoId, respostaId, divPreview) {
    const resp = await fetch(`/api/arquivo/${arquivoId}/resposta/${respostaId}`);
    const blob = await resp.blob();
    const blobUrl = URL.createObjectURL(blob);

    if (blob.type.startsWith("image/")) {
        divPreview.innerHTML = `<img src="${blobUrl}" style="max-width:300px;">`;
    }
    else if (blob.type === "application/pdf") {
        divPreview.innerHTML = `
            <embed src="${blobUrl}" width="100%" height="500px" type="application/pdf">
        `;
    }
    else if (blob.type.startsWith("text/")) {
        const texto = await blob.text();
        divPreview.textContent = texto;
    }
    else {
        divPreview.textContent = "Pré-visualização não suportada.";
    }
}


function downloadArquivo(nomeArquivo) {
    const url = `/api/arquivo/${nomeArquivo}`;
    window.open(url, "_blank");
}

function validaInputNota(inputNota) {
    let valor = inputNota.value;

    valor = valor.replace(/[^0-9,.]/g, "");

    valor = valor.replace(/\./g, ",");

    let partes = valor.split(",");
    if (partes.length > 2) {
        valor = partes[0] + "," + partes.slice(1).join("");
        partes = valor.split(",");
    }

    if (partes[1] && partes[1].length > 2) {
        partes[1] = partes[1].substring(0, 2);
        valor = partes[0] + "," + partes[1];
    }

    if (parseFloat(valor.replace(",", ".")) > dadosDesempenho.valorAtividade) {
        valor = String(dadosDesempenho.valorAtividade);
    }

    inputNota.value = valor;
}

function enviarNota() {
    const inputNota = document.getElementById("input-nota");
    let nota = inputNota.value;

    if (nota === "") {
        alert("Insira uma nota antes de submeter!");
        destacarCampo(inputNota);
        return;
    }

    nota = Number(nota.replace(",", "."));

    if (confirm(`A nota ${nota} será atribuida`) == false) return;

    fetch(`/api/desempenho/${dadosDesempenho.desempenhoId}/nota`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nota })
    })
        .then(resp => {
            if (!resp.ok) throw new Error("Erro ao submeter a nota");
            return resp.json();
        })
        .then(() => {
            alert("Nota submetida com sucesso");
            window.location.href = "/home";
        })
        .catch(err => {
            alert("Erro: " + err.message);
        });
}

function destacarCampo(campo) {
    campo.classList.add("campo-obrigatorio");
    setTimeout(() => campo.classList.remove("campo-obrigatorio"), 2000);
    campo.focus();
}

function confirmarSaidaDaPag() {
    if (confirm("Tem certeza que deseja sair da página? Os dados da atividade serão perdidos?")) {
        window.location.href = "/home";
    }
}

function initAvaliacao() {
    carregarResposta();
}

window.onload = carregarResposta;