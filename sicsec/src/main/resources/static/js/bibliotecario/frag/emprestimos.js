class GerenciadorCriacaoEmprestimosReservas {
    constructor() {
        this.form = document.getElementById('criarEmprestimoForm');
        this.init();
    }

    init() {
        this.codigoInput = document.getElementById('codigo');
        this.tipoInput = document.getElementById('tipo');
        this.dataInput = document.getElementById('data');
        this.duracaoInput = document.getElementById('duracao');
        this.matriculaInput = document.getElementById('matricula');
        this.btnEnviar = this.form.querySelector('button[type="submit"]');

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.criarEmprestimoOuReserva();
        });
    }

    async criarEmprestimoOuReserva() {
        const codigo = this.codigoInput.value.trim();
        const tipo = this.tipoInput.value.trim();
        const dataInput = this.dataInput.value;
        const duracao = Number(this.duracaoInput.value);
        const matricula = Number(this.matriculaInput.value);

        if (!codigo || !tipo || !duracao || !matricula) {
            this.mostrarMensagem('Preencha todos os campos obrigatórios', 'erro');
            return;
        }

        if (!Number.isInteger(duracao) || duracao < 1) {
            this.mostrarMensagem('Duração inválida', 'erro');
            return;
        }

        if (!Number.isInteger(matricula)) {
            this.mostrarMensagem('Número de matrícula inválido', 'erro');
            return;
        }

        let dataMillis = null;

        if (dataInput) {
            const data = new Date(dataInput);

            if (isNaN(data.getTime())) {
                this.mostrarMensagem('Data inválida', 'erro');
                return;
            }

            const hoje = new Date();
            hoje.setHours(0, 0, 0, 0);

            const dataInformada = new Date(data);
            dataInformada.setHours(0, 0, 0, 0);

            if (dataInformada < hoje) {
                this.mostrarMensagem('A data não pode estar no passado', 'erro');
                return;
            }

            dataMillis = dataInformada.getTime();
        }

        this.btnEnviar.disabled = true;
        this.btnEnviar.textContent = 'Salvando...';

        try {
            const formData = new FormData();
            formData.append('codigo', codigo);
            formData.append('tipo', tipo);
            formData.append('duracao', duracao);
            formData.append('matricula', matricula);

            if (dataMillis !== null) {
                formData.append('dataExpiracao', dataMillis.toString());
            }

            const response = await fetch('/api/emprestimos/criar', {
                method: 'POST',
                body: formData
            });

            const resultado = await response.json();

            if (response.ok) {
                this.mostrarMensagem('Empréstimo/Reserva criado com sucesso!', 'sucesso');
                this.limparFormulario();
            } else {
                this.mostrarMensagem(resultado || 'Erro ao criar empréstimo', 'erro');
            }

        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        } finally {
            this.btnEnviar.disabled = false;
            this.btnEnviar.textContent = 'Criar Empréstimo ou Reserva';
        }
    }

    mostrarMensagem(mensagem, tipo) {
        alert(`${tipo.toUpperCase()}: ${mensagem}`);
    }

    limparFormulario() {
        this.form.reset();
    }
}

class GerenciadorEdicaoEmprestimosReservas {
    constructor() {
        this.modal = document.getElementById('modalEditarEmprestimo');
        this.form = document.getElementById('formEditarEmprestimo');
        this.listaEmprestimos = document.getElementById('listaEmprestimos');
        this.emprestimoAtual = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.carregarEmprestimos();
    }

    setupEventListeners() {
        document.getElementById('buscarMatricula')
            .addEventListener('input', () => this.carregarEmprestimos());

        document.getElementById('filtroReserva')
            .addEventListener('change', () => this.carregarEmprestimos());

        this.form.querySelector('.btn-cancelar')
            .addEventListener('click', () => this.fecharModal());

        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.salvarEdicao();
        });

        this.modal.addEventListener('click', (e) => {
            if (e.target === this.modal) this.fecharModal();
        });
    }

    formatarData(data) {
        const d = new Date(data);
        return d.toLocaleDateString('pt-BR');
    }

    async carregarEmprestimos() {
        const matricula = document.getElementById('buscarMatricula').value;
        const status = document.getElementById('filtroReserva').value;

        try {
            const response = await fetch(
                `/api/emprestimos/listar?matricula=${encodeURIComponent(matricula)}&status=${status}`
            );

            if (!response.ok) throw new Error();
            const emprestimos = await response.json();
            this.mostrarEmprestimos(emprestimos);

        } catch {
            this.mostrarMensagem('Erro ao carregar empréstimos', 'erro');
        }
    }

    mostrarEmprestimos(emprestimos) {
        if (!emprestimos.length) {
            this.listaEmprestimos.innerHTML = `<p>Nenhum empréstimo encontrado</p>`;
            return;
        }

        this.listaEmprestimos.innerHTML = emprestimos.map(e => `
            <div class="emprestimo-item" data-id="${e.id}">
                <div class="emprestimo-info">
                    <h4>${e.livro.titulo}</h4>
                    <div class="emprestimo-meta">
                        <span>Código: ${e.livro.codigo}</span>
                        <span>Usuário: ${e.usuario.nome} (${e.usuario.matricula})</span>
                        <span>Data: ${this.formatarData(e.data)}</span>
                        <span class="emprestimo-status status-${e.reserva.toLowerCase()}">${e.reserva}
                        </span>
                        <span>Duração: ${e.duracao} dias</span>
                    </div>
                </div>
                ${e.reserva !== "HISTORICO" ? 
                    `<div class="emprestimo-acoes">
                        <button class="btn-editar">Editar</button>
                    </div>` 
                : ''}
            </div>
        `).join('');

        this.listaEmprestimos.querySelectorAll('.btn-editar').forEach(btn => {
            btn.addEventListener('click', () => {
                const id = btn.closest('.emprestimo-item').dataset.id;
                this.abrirEdicao(id);
            });
        });
    }

    async abrirEdicao(id) {
        try {
            const response = await fetch(`/api/emprestimos/${id}`);
            if (!response.ok) throw new Error();
            this.emprestimoAtual = await response.json();
            this.preencherFormulario(this.emprestimoAtual);
            this.abrirModal();
        } catch {
            this.mostrarMensagem('Erro ao carregar empréstimo', 'erro');
        }
    }

    preencherFormulario(e) {
        document.getElementById('editarEmprestimoId').value = e.id;
        document.getElementById('editarDuracao').value = e.duracao;
        
        if(e.reserva === "EMPRESTIMO" || e.reserva === "RESERVA") {
            document.getElementById('editarReserva').checked = false;
        }

        document.getElementById('editarMatricula').value = e.usuario.matricula;
    }

    async salvarEdicao() {
        const id = document.getElementById('editarEmprestimoId').value;

        const duracao = Number(document.getElementById('editarDuracao').value);
        const historico = document.getElementById('editarReserva').checked;
        const matricula = document.getElementById('editarMatricula').value;

        if (!Number.isInteger(duracao) || duracao < 1) {
            this.mostrarMensagem('Duração inválida', 'erro');
            return;
        }

        const formData = new FormData();
        formData.append('duracao', duracao);
        formData.append('historico', historico);
        formData.append('matricula', matricula);

        try {
            const response = await fetch(`/api/emprestimos/${id}`, {
                method: 'PUT',
                body: formData
            });

            const resultado = await response.text();

            if (response.ok) {
                this.mostrarMensagem('Empréstimo atualizado com sucesso!', 'sucesso');
                this.fecharModal();
                this.carregarEmprestimos();
            } else {
                this.mostrarMensagem(resultado || 'Erro ao atualizar empréstimo', 'erro');
            }

        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        }
    }

    abrirModal() {
        this.modal.style.display = 'block';
    }

    fecharModal() {
        this.modal.style.display = 'none';
        this.form.reset();
    }

    mostrarMensagem(msg, tipo) {
        alert(`${tipo.toUpperCase()}: ${msg}`);
    }
}

class GerenciadorEmprestimosReservas {
    constructor() {
        this.container = document.querySelector('#emprestimos-content');
        this.gerenciadorCriacao = null;
        this.gerenciadorEdicao = null;
        this.init();
    }

    init() {
        this.gerenciadorTabs = new GerenciadorTabs(this.container);
        this.setupTabChangeListener();
        this.inicializarCriacao();
    }

    setupTabChangeListener() {
        this.container.addEventListener('tabChange', (e) => {
            const tabId = e.detail.tabId;

            if (tabId === 'criar') {
                this.inicializarCriacao();
            } else if (tabId === 'editar') {
                this.inicializarEdicao();
            }
        });
    }

    inicializarCriacao() {
        if (!this.gerenciadorCriacao) {
            this.gerenciadorCriacao = new GerenciadorCriacaoEmprestimosReservas();
        }
    }

    inicializarEdicao() {
        if (!this.gerenciadorEdicao) {
            this.gerenciadorEdicao = new GerenciadorEdicaoEmprestimosReservas();
        }
    }

    mudarParaTab(tabId) {
        this.gerenciadorTabs.mudarParaTab(tabId);
    }

    getTabAtual() {
        return this.gerenciadorTabs.getTabAtual();
    }
}

new GerenciadorEmprestimosReservas();
