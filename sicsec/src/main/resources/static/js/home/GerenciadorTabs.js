class GerenciadorTabs {
    constructor(container) {
        this.container = container;
        this.tabs = this.container.querySelectorAll('header button');
        this.tabContents = this.container.querySelectorAll('.tab');
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.ativarPrimeiraTab();
    }

    setupEventListeners() {
        this.tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                const tabId = tab.getAttribute('data-tab');
                this.ativarTab(tabId);
            });
        });
    }

    ativarPrimeiraTab() {
        if (this.tabs.length > 0) {
            const primeiraTab = this.tabs[0].getAttribute('data-tab');
            this.ativarTab(primeiraTab);
        }
    }

    ativarTab(tabId) {
        // Desativa todas as tabs
        this.tabs.forEach(tab => {
            tab.classList.remove('ativo');
        });

        this.tabContents.forEach(content => {
            content.classList.remove('ativo');
        });

        // Ativa a tab selecionada
        const tabAtiva = this.container.querySelector(`header button[data-tab="${tabId}"]`);
        const conteudoAtivo = this.container.querySelector(`.tab.${tabId}`);

        if (tabAtiva && conteudoAtivo) {
            tabAtiva.classList.add('ativo');
            conteudoAtivo.classList.add('ativo');

            // Dispara evento customizado quando a tab é ativada
            this.container.dispatchEvent(new CustomEvent('tabChange', {
                detail: { tabId }
            }));
        }
    }

    mudarParaTab(tabId) {
        this.ativarTab(tabId);
    }

    getTabAtual() {
        const tabAtiva = this.container.querySelector('header button.ativo');
        return tabAtiva ? tabAtiva.getAttribute('data-tab') : null;
    }
}