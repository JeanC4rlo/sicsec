class SectionManager {
    constructor(navSelector = "nav button", sectionSelector = "main > section") {
        this.navButtons = document.querySelectorAll(navSelector);
        this.sections = document.querySelectorAll(sectionSelector);
        this.activeSectionId = localStorage.getItem("secaoAtiva") || (this.sections[0]?.id || null);
        this.initSections = ["matriculas", "turmas"];
    }

    init() {
        this.loadSections();
        this.setupNavigation();
        if (this.activeSectionId) this.showSection(this.activeSectionId);
    }

    setupNavigation() {
        this.navButtons.forEach(btn => {
            btn.addEventListener("click", e => {
                e.preventDefault();
                this.showSection(btn.dataset.section);
            });
        });
    }

    showSection(id) {
        this.sections.forEach(s => s.classList.toggle("ativo", s.id === id));
        this.navButtons.forEach(b => b.classList.toggle("ativo", b.dataset.section === id));
        localStorage.setItem("secaoAtiva", id);
        if (this.initSections.includes(id)) this.runInit(id);
    }

    loadSections() {
        this.sections.forEach(section => {
            const wrapper = section.querySelector(".wrapper");
            if (!wrapper) return;
            fetch(`/html/admin/${section.id}.html`)
                .then(resp => resp.text())
                .then(html => {
                    wrapper.innerHTML = html;
                    if (this.initSections.includes(section.id)) this.runInit(section.id);
                });
        });
    }

    runInit(id) {
        switch (id) {
            case "matriculas":
                initMatriculas();
                break;
            case "turmas":
                initTurmas();
                break;
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    new SectionManager().init();
});
