async function loadTabs() {

    initAcervo();
    
    let matricula = new URL(window.location.href).searchParams.get("matricula");
    if (matricula) {
        
        return
    }
    
    const tabMeusLivros = document.querySelector(".tab#meusLivros")
    const resposta = await fetch(`frag/login.html`);
    const _html = await resposta.text();
    tabMeusLivros.innerHTML = _html;
    tabMeusLivros.classList += " login";
    initLoginBiblioteca();

}

function highlightBibliotecaOption(tabId) {
    const buttons = document.querySelectorAll("#biblioteca header button");

    buttons.forEach(button => {
        if (button.dataset.tab === tabId)
            button.classList.add("ativo");
            
        else
            button.classList.remove("ativo");
    });
}

function openBibliotecaTab(tabId) {

    const tabs = document.querySelectorAll(`.tab`);
    tabs.forEach(tab => {
        if (tab.id === tabId)
            tab.classList.add("ativo");
        else
            tab.classList.remove("ativo");
    });

    localStorage.setItem("bibliotecaSecaoAtiva", tabId);
}

function initBiblioteca() {

    loadTabs();


    const bibliotecaButtons = document.querySelectorAll("#biblioteca header button");
    bibliotecaButtons.forEach(button => {
        if (!button.dataset.tab)
            button.dataset.tab = button.textContent.trim().toLowerCase();

        button.addEventListener("click", e => {
            e.preventDefault();
            const targetTab = button.dataset.tab;
            highlightBibliotecaOption(targetTab);
            openBibliotecaTab(targetTab);
        });
    });

    const ultimaSecao = localStorage.getItem("bibliotecaSecaoAtiva");

    if (ultimaSecao) {
        highlightBibliotecaOption(ultimaSecao);
        openBibliotecaTab(ultimaSecao);
    } else {
        highlightBibliotecaOption("acervo");
        openBibliotecaTab("acervo");
    }

}