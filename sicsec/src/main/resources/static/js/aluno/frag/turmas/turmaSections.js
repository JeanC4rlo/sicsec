function highlightTurmasOption(tabId) {
    const buttons = document.querySelectorAll("#turmas-content .acoes-container button");

    buttons.forEach(button => {
        if (button.dataset.tab === tabId)
            button.classList.add("ativo");
        else
            button.classList.remove("ativo");
    });
}

function openTurmasTab(tabId) {

    const sections = document.querySelectorAll("#turmas-content section");

    sections.forEach(section => {
        if (section.classList.contains(tabId))
            section.classList.add("ativo");
        else
            section.classList.remove("ativo");
    });

}

function switchTurmasTab(targetTab) {
    highlightTurmasOption(targetTab);
    openTurmasTab(targetTab);
}

function initTurmasTabs() {
    
    const turmasButtons = document.querySelectorAll("#turmas-content .acoes-container button");

    turmasButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault();
            const targetTab = button.dataset.tab;
            switchTurmasTab(targetTab);
        });
    });

    switchTurmasTab("principal");

}
