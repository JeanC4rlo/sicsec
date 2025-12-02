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
            if (dadosDesempenho.tipoAtividade == "Redação")
                montarTelaRedacao();
            else
                montarTelaEnvioArquivo();
        })
        .catch(err => {
            console.error(err);
        })
}

function montarTelaEnvioArquivo() {
    const nomeAluno = document.createElement("p");
    const turmaAluno = document.createElement("p");
    const inputArquivo = document.createElement("input");
    const divPreview = document.createElement("div");
    const btnBaixarArquivo = document.createElement("button");
    const inputNota = document.createElement("input");
    const btnEnviarNota = document.createElement("button");

    nomeAluno.innerHTML = dadosDesempenho.nomeAluno;
    turmaAluno.innerHTML = "TURMA ALUNO";

    inputArquivo.type = "file";

    carregarPreview(dadosDesempenho.nomeArquivo, divPreview);

    btnBaixarArquivo.textContent = "Baixar arquivo";
    btnBaixarArquivo.addEventListener("click", () => downloadArquivo(dadosDesempenho.nomeArquivo));

    inputNota.id = "input-nota";
    inputNota.type = "text";
    inputNota.placeholder = "Insirir nota";
    inputNota.min = 0;
    inputNota.max = 100;
    inputNota.addEventListener("input", () => validaInputNota(inputNota));

    btnEnviarNota.textContent = "Submeter nota";
    btnEnviarNota.addEventListener("click", enviarNota);

    divConteudo.append(nomeAluno, turmaAluno, divPreview, btnBaixarArquivo, inputNota, btnEnviarNota);
}

async function carregarPreview(nomeArquivo, divPreview) {
    const resp = await fetch(`/api/arquivo/${nomeArquivo}`);
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

function montarTelaRedacao() {
    const nomeAluno = document.createElement("p");
    const turmaAluno = document.createElement("p");
    const redacao = document.createElement("p");
    const inputNota = document.createElement("input");
    const btnEnviarNota = document.createElement("button");

    nomeAluno.innerHTML = "NOME ALUNO";
    turmaAluno.innerHTML = "TURMA ALUNO";
    redacao.innerHTML = dadosDesempenho.redacao;

    inputNota.id = "input-nota";
    inputNota.type = "text";
    inputNota.placeholder = "Insirir nota";
    inputNota.min = 0;
    inputNota.max = 100;
    inputNota.addEventListener("input", () => validaInputNota(inputNota));

    btnEnviarNota.textContent = "Submeter nota";
    btnEnviarNota.addEventListener("click", enviarNota);

    divConteudo.append(nomeAluno, turmaAluno, redacao, inputNota, btnEnviarNota);
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

    if(confirm(`A nota ${nota} será atribuida`) == false) return;

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
            window.location.href = "/homeProfessor";
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

function initAvaliacao() {
    carregarResposta();
}

window.onload = carregarResposta;