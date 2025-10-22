let btnSair;
let form;
let btnAnterior;
let btnProximo;

window.onload = initProdAtividade;

function inativarSections() {
    const sections = document.querySelectorAll("section");
    sections.forEach(section => {
        section.classList.remove("ativo");
    });
    form.classList.remove("inativo");
}

function passarPagina() {
    const section = document.getElementById(`section-${form.tipo.value}`);
    section.classList.add("ativo");
    form.classList.add("inativo");
}

function initProdAtividade() {
    btnSair = document.getElementById("btn-sair");
    form = document.querySelector("form");
    btnAnterior = document.getElementById("btn-anterior");
    btnProximo = document.getElementById("btn-proximo");

    btnProximo.addEventListener('click', passarPagina);
    btnAnterior.addEventListener('click', inativarSections);
}