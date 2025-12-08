class GerenciadorUsuarios {
    constructor() {
        this.usuariosSelecionados = new Map();
        this.debounceTimeout = null;
        this.init();
    }

    init() {
        this.buscarInput = document.getElementById('buscarUsuario');
        this.resultadosDiv = document.getElementById('resultadosUsuarios');
        this.selecionadosDiv = document.getElementById('usuariosSelecionados');
        this.hiddenInput = document.getElementById('usuariosIds');

        this.setupEventListeners();
        this.atualizarInterface();
    }

    setupEventListeners() {
        // Debounce na busca
        this.buscarInput.addEventListener('input', (e) => {
            clearTimeout(this.debounceTimeout);
            const termo = e.target.value.trim();
            
            if (termo.length < 2) {
                this.fecharResultados();
                return;
            }

            this.debounceTimeout = setTimeout(() => this.buscarUsuarios(termo), 500);
        });

        // Fechar resultados ao clicar fora
        document.addEventListener('click', (e) => {
            if (!this.buscarInput.contains(e.target) && !this.resultadosDiv.contains(e.target)) {
                this.fecharResultados();
            }
        });

        // Backspace remove último chip
        this.buscarInput.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && !this.buscarInput.value && this.usuariosSelecionados.size > 0) {
                const lastUserId = [...this.usuariosSelecionados.keys()].pop();
                this.removerUsuario(lastUserId);
            }
        });

        // Delegation para remover chips (resolve o problema do X)
        this.selecionadosDiv.addEventListener('click', (e) => {
            if (e.target.classList.contains('chip-remover')) {
                const usuarioId = parseInt(e.target.dataset.usuarioId);
                this.removerUsuario(usuarioId);
            }
        });
    }

    async buscarUsuarios(termo) {
        try {
            const response = await fetch(`/api/usuarios/buscar?query=${encodeURIComponent(termo)}`);
            const usuarios = await response.json();
            this.mostrarResultados(usuarios);
        } catch (error) {
            console.error('Erro ao buscar usuários:', error);
        }
    }

    mostrarResultados(usuarios) {
        const usuariosDisponiveis = usuarios.filter(u => !this.usuariosSelecionados.has(u.id));
        
        if (usuariosDisponiveis.length === 0) {
            this.resultadosDiv.innerHTML = '<div class="resultado-item">Nenhum usuário encontrado</div>';
        } else {
            this.resultadosDiv.innerHTML = usuariosDisponiveis.map(usuario => `
                <div class="resultado-item" data-usuario-id="${usuario.id}">
                    <div class="info-usuario">
                        <strong>${usuario.nome}</strong>
                        <small>${usuario.matricula} • ${usuario.cpf}</small>
                    </div>
                </div>
            `).join('');
        }

        // Event delegation para adicionar usuários
        this.resultadosDiv.addEventListener('click', (e) => {
            const item = e.target.closest('.resultado-item');
            if (!item) return;

            const usuarioId = parseInt(item.dataset.usuarioId);
            const usuario = usuarios.find(u => u.id === usuarioId);
            
            if (usuario) {
                this.adicionarUsuario(usuario);
                this.fecharResultados();
                this.buscarInput.value = '';
            }
        });

        this.resultadosDiv.style.display = 'block';
    }

    fecharResultados() {
        this.resultadosDiv.style.display = 'none';
    }

    adicionarUsuario(usuario) {
        if (this.usuariosSelecionados.has(usuario.id)) return;
        
        this.usuariosSelecionados.set(usuario.id, usuario);
        this.atualizarInterface();
        this.buscarInput.focus();
    }

    removerUsuario(usuarioId) {
        this.usuariosSelecionados.delete(usuarioId);
        this.atualizarInterface();
        this.buscarInput.focus();
    }

    atualizarInterface() {
        // Atualiza chips
        this.selecionadosDiv.innerHTML = Array.from(this.usuariosSelecionados.values()).map(usuario => `
            <div class="chip">
                <div class="info-usuario">
                    <strong>${usuario.nome}</strong>
                    <small>${usuario.matricula} • ${usuario.cpf}</small>
                </div>
                <button type="button" class="chip-remover" data-usuario-id="${usuario.id}">×</button>
            </div>
        `).join('');

        // Adiciona input no final
        this.selecionadosDiv.appendChild(this.buscarInput);
        
        // Atualiza placeholder e hidden input
        this.buscarInput.placeholder = this.usuariosSelecionados.size === 0 
            ? 'Buscar usuários...' 
            : 'Adicionar mais usuários...';

        this.hiddenInput.value = Array.from(this.usuariosSelecionados.keys()).join(',');
    }

    getUsuariosSelecionados() {
        return Array.from(this.usuariosSelecionados.values());
    }

    getUsuariosIds() {
        return Array.from(this.usuariosSelecionados.keys());
    }

    limparSelecao() {
        this.usuariosSelecionados.clear();
        this.atualizarInterface();
    }
}

new GerenciadorUsuarios();