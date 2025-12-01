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
    
    console.log("Abrindo aba:", tabId);

    const sections = document.querySelectorAll("#turmas section");

    sections.forEach(section => {
        if (section.classList.contains(tabId))
            section.classList.add("ativo");
        else
            section.classList.remove("ativo");
    });

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

}
