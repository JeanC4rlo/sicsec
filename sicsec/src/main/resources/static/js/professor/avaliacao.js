let dadosResposta;
const divConteudo = document.getElementById("conteudo");

function carregarResposta() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    fetch(`/desempenho/${id}`)
        .then(resp => {
            if (!resp.ok) throw new Error("Erro ao carregar a tentativa");
            return resp.json();
        })
        .then(resp => {
            dadosResposta = resp;
            if (dadosResposta.tipoAtividade == "Redação")
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

    nomeAluno.innerHTML = "NOME ALUNO";
    turmaAluno.innerHTML = "TURMA ALUNO";

    inputArquivo.type = "file";

    console.log(dadosResposta.nomeArquivo);

    carregarPreview(dadosResposta.nomeArquivo, divPreview);

    btnBaixarArquivo.textContent = "Baixar arquivo";
    btnBaixarArquivo.addEventListener("click", () => downloadArquivo(dadosResposta.nomeArquivo));

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

    const resp = await fetch(`/arquivo/${nomeArquivo}`);

    // Debug sem consumir o stream
    console.log("Content-Type enviado pelo backend:", resp.headers.get("Content-Type"));

    const blob = await resp.blob();

    console.log("blob.type:", blob.type); // aqui vai mostrar o tipo detectado

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
    const url = `/arquivo/download/${nomeArquivo}`;
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
    redacao.innerHTML = dadosResposta.redacao;

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

    if (parseFloat(valor.replace(",", ".")) > 100) {
        valor = "100";
    }

    inputNota.value = valor;
}

function enviarNota() {
    let nota = document.getElementById("input-nota").value;

    if (nota === "") {
        alert("Insira uma nota antes de submeter!");
        return;
    }

    nota = Number(nota.replace(",", "."));

    console.log(dadosResposta.desempenhoId);

    fetch(`/atribuir/${dadosResposta.desempenhoId}/nota`, {
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


function initAvaliacao() {
    carregarResposta();
}

window.onload = carregarResposta;