function montarDescricao(tipoAtividade, itemLista, listaDesempenhosHTML, desempenhoId) {
    const linhaAvaliar = document.createElement("li");
    const btnAvaliar = document.createElement("button");

    itemLista.addEventListener("click", () => toggleAvaliar(linhaAvaliar))

    console.log("teste");

    linhaAvaliar.classList.add("inativo");
    switch (tipoAtividade) {
        case "Redação":
            montarDescricaoRedacao(linhaAvaliar, btnAvaliar, desempenhoId);
            break;
        case "Envio de Arquivo":
            montarDescricaoEnvioArquivo(linhaAvaliar, btnAvaliar, desempenhoId);
            break;
        default:
            return;
    }
    listaDesempenhosHTML.append(linhaAvaliar);
}

function montarDescricaoRedacao(linhaDescricao, btnAvaliar, id) {
    btnAvaliar.textContent = "Avaliar";
    linhaDescricao.append(btnAvaliar);
    btnAvaliar.addEventListener("click", () => {
        window.location.href = `/html/professor/avaliacao.html?id=${id}&tipo=redacao`;
    });

    return linhaDescricao;
}

function montarDescricaoEnvioArquivo(linhaDescricao, btnAvaliar, id) {
    btnAvaliar.textContent = "Avalir";
    linhaDescricao.append(btnAvaliar);
    btnAvaliar.addEventListener("click", () => {
        window.location.href = `/html/professor/avaliacao.html?id=${id}&tipo=env_arquivo`;
    });

    return linhaDescricao;
}

function toggleAvaliar(linhaAvaliar) {
    linhaAvaliar.classList.contains("inativo") ? linhaAvaliar.classList.remove("inativo") : linhaAvaliar.classList.add("inativo");
}