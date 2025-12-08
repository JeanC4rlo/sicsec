function highlightBolsasOption(tabId) {
    const buttons = document.querySelectorAll("#bolsas header button");

    buttons.forEach(button => {
        if (button.dataset.tab === tabId)
            button.classList.add("ativo");
        else
            button.classList.remove("ativo");
    });
}

function openBolsasTab(tabId) {
    const tabs = document.querySelectorAll("#bolsas .wrapper > div");

    tabs.forEach(tab => {
        if (tab.classList.contains(tabId))
            tab.classList.add("ativo");
        else
            tab.classList.remove("ativo");
    });

    localStorage.setItem("bolsasSecaoAtiva", tabId);
}

function initBolsasTabs() {
    const bolsasButtons = document.querySelectorAll("#bolsas header button");

    bolsasButtons.forEach(button => {
        if (!button.dataset.tab)
            button.dataset.tab = button.textContent.trim().toLowerCase().replace(/\s+/g, "-");

        button.addEventListener("click", e => {
            e.preventDefault();
            const targetTab = button.dataset.tab;
            highlightBolsasOption(targetTab);
            openBolsasTab(targetTab);
        });
    });

    const ultimaSecao = localStorage.getItem("bolsasSecaoAtiva");

    if (ultimaSecao) {
        highlightBolsasOption(ultimaSecao);
        openBolsasTab(ultimaSecao);
    } else {
        highlightBolsasOption("pesquisa");
        openBolsasTab("pesquisa");
    }
}

initBolsasTabs();
