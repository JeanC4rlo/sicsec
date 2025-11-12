function highlightTurmasOption(tabId) {
    const buttons = document.querySelectorAll("#turmas .acoes-container button");

    buttons.forEach(button => {
        if (button.dataset.tab === tabId)
            button.classList.add("ativo");
        else
            button.classList.remove("ativo");
    });
}

function openTurmasTab(tabId) {
    const sections = document.querySelectorAll("#turmas section");

    sections.forEach(section => {
        if (section.classList.contains(tabId))
            section.classList.add("ativo");
        else
            section.classList.remove("ativo");
    });

    localStorage.setItem("turmasSecaoAtiva", tabId);
}

function initTurmasTabs() {
    const turmasButtons = document.querySelectorAll("#turmas .acoes-container button");

    turmasButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            const targetTab = button.dataset.tab;
            highlightTurmasOption(targetTab);
            openTurmasTab(targetTab);
        });
    });

    const ultimaSecao = localStorage.getItem("turmasSecaoAtiva");

    if (ultimaSecao) {
        highlightTurmasOption(ultimaSecao);
        openTurmasTab(ultimaSecao);
    } else {
        // Define a seção padrão como "participantes"
        highlightTurmasOption("participantes");
        openTurmasTab("participantes");
    }
}
