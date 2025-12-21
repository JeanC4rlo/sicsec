class GerenciadorCriacaoDocumentos {
    constructor() {
        this.gerenciadorUsuarios = new GerenciadorUsuarios(document.querySelector("#documentos-content .usuarios-section"));
        this.form = document.getElementById('criarDocumentoForm');
        this.init();
    }

    init() {
        this.tituloInput = document.getElementById('titulo');
        this.arquivoInput = document.getElementById('conteudo');
        this.dataExpiracaoInput = document.getElementById('dataExpiracao');
        this.btnEnviar = this.form.querySelector('button[type="submit"]');

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.enviarDocumento();
        });

        this.arquivoInput.addEventListener('change', (e) => {
            this.validarArquivo(e.target.files[0]);
        });
    }

    validarArquivo(arquivo) {
        if (!arquivo) return true;

        const tamanhoEmMB = 15;
        const tamanhoMaximo = tamanhoEmMB * 1024 * 1024;
        const tiposPermitidos = [
            'application/pdf',
            'application/msword',
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
            'image/jpeg',
            'image/png',
            'text/plain'
        ];

        if (arquivo.size > tamanhoMaximo) {
            this.mostrarMensagem(`Arquivo muito grande. Tamanho máximo: ${tamanhoEmMB}MB`, 'erro');
            this.arquivoInput.value = '';
            return false;
        }

        if (!tiposPermitidos.includes(arquivo.type)) {
            this.mostrarMensagem('Tipo de arquivo não permitido. Use PDF, Word, Imagens ou TXT', 'erro');
            this.arquivoInput.value = '';
            return false;
        }

        return true;
    }

    async enviarDocumento() {
        const arquivo = this.arquivoInput.files[0];
        const titulo = this.tituloInput.value.trim();
        const usuariosIds = this.gerenciadorUsuarios.getUsuariosIds();

        // Validações
        if (!titulo) {
            this.mostrarMensagem('Digite um título para o documento', 'erro');
            return;
        }

        if (!arquivo) {
            this.mostrarMensagem('Selecione um arquivo', 'erro');
            return;
        }

        if (usuariosIds.length === 0) {
            this.mostrarMensagem('Selecione pelo menos um usuário', 'erro');
            return;
        }

        if (!this.validarArquivo(arquivo)) {
            return;
        }

        this.btnEnviar.disabled = true;
        this.btnEnviar.textContent = 'Enviando...';

        try {
            const formData = new FormData();
            formData.append('conteudo', arquivo);
            formData.append('titulo', titulo);
            formData.append('usuariosIds', usuariosIds.join(','));

            if (this.dataExpiracaoInput.value) {
                const data = new Date(this.dataExpiracaoInput.value);
                if (!isNaN(data.getTime())) {
                    const dataMillis = data.getTime();
                    formData.append('dataExpiracao', dataMillis.toString());
                } else {
                    this.mostrarMensagem('Data de expiração inválida', 'erro');
                    this.btnEnviar.disabled = false;
                    this.btnEnviar.textContent = 'Enviar Documento';
                    return;
                }
            }

            const response = await fetch('/api/documentos/upload', {
                method: 'POST',
                body: formData
            });

            let resultado;
            try {
                resultado = await response.json();
            } catch (jsonError) {
                throw new Error('Resposta inválida do servidor');
            }

            if (response.ok) {
                this.mostrarMensagem('Documento enviado com sucesso!', 'sucesso');
                this.limparFormulario();
                this.gerenciadorUsuarios.limparSelecao();
            } else {
                this.mostrarMensagem(`Erro: ${resultado.detalhes || resultado.erro || 'Erro ao enviar documento'}`, 'erro');
            }

        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        } finally {
            this.btnEnviar.disabled = false;
            this.btnEnviar.textContent = 'Enviar Documento';
        }
    }

    mostrarMensagem(mensagem, tipo) {
        alert(`${tipo.toUpperCase()}: ${mensagem}`);
    }

    limparFormulario() {
        this.form.reset();
    }
}

class GerenciadorEdicaoDocumentos {
    constructor() {
        this.modal = document.getElementById('modalEditarDocumento');
        this.form = document.getElementById('formEditarDocumento');
        this.listaDocumentos = document.getElementById('listaDocumentos');
        this.documentoAtual = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.carregarDocumentos();
    }

    setupEventListeners() {
        document.getElementById('buscarDocumento').addEventListener('input', () => {
            this.carregarDocumentos();
        });

        document.getElementById('filtroStatus').addEventListener('change', () => {
            this.carregarDocumentos();
        });

        document.querySelector('.btn-cancelar').addEventListener('click', () => {
            this.fecharModal();
        });

        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.salvarEdicao();
        });

        this.modal.addEventListener('click', (e) => {
            if (e.target === this.modal) {
                this.fecharModal();
            }
        });
    }

    async carregarDocumentos() {
        const busca = document.getElementById('buscarDocumento').value;
        const status = document.getElementById('filtroStatus').value;

        try {
            const response = await fetch(`/api/documentos/listar?busca=${encodeURIComponent(busca)}&status=${status}`);

            if (!response.ok) {
                throw new Error('Erro ao carregar documentos');
            }

            const documentos = await response.json();
            this.mostrarDocumentos(documentos);
        } catch (error) {
            console.error('Erro ao carregar documentos:', error, error.stack);
            this.mostrarMensagem('Erro ao carregar documentos', 'erro');
        }
    }

    mostrarDocumentos(documentos) {
        if (documentos.length === 0) {
            this.listaDocumentos.innerHTML = `
                <div class="documento-vazio">
                    <p>Nenhum documento encontrado</p>
                </div>
            `;
            return;
        }

        this.listaDocumentos.innerHTML = documentos.map(doc => `
            <div class="documento-item" data-documento-id="${doc.id}">
                <div class="documento-info">
                    <h4>${doc.titulo}</h4>
                    <div class="documento-meta">
                        <span class="documento-status status-${doc.status.toLowerCase()}">${doc.status}</span>
                        <span>Criado: ${new Date(doc.dataCriacao).toLocaleDateString()}</span>
                        ${doc.dataExpiracao ? `<span>Expira: ${new Date(doc.dataExpiracao).toLocaleDateString()}</span>` : ''}
                    </div>
                </div>
                <div class="documento-acoes">
                    <button type="button" class="btn-editar">Editar</button>
                </div>
            </div>
        `).join('');

        this.listaDocumentos.querySelectorAll('.btn-editar').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                const documentoId = btn.closest('.documento-item').dataset.documentoId;
                this.abrirEdicao(documentoId);
            });
        });
    }

    async abrirEdicao(documentoId) {
        try {
            const response = await fetch(`/api/documentos/${documentoId}`);

            if (!response.ok) {
                throw new Error('Documento não encontrado');
            }

            this.documentoAtual = await response.json();
            this.preencherFormulario(this.documentoAtual);
            this.abrirModal();
        } catch (error) {
            console.error('Erro ao carregar documento:', error);
            this.mostrarMensagem('Erro ao carregar documento', 'erro');
        }
    }

    preencherFormulario(documento) {
        document.getElementById('editarDocumentoId').value = documento.id;
        document.getElementById('editarTitulo').value = documento.titulo;

        if (documento.dataExpiracao) {
            const data = new Date(documento.dataExpiracao);
            document.getElementById('editarDataExpiracao').value =
                data.toISOString().slice(0, 16);
        } else {
            document.getElementById('editarDataExpiracao').value = '';
        }

        document.getElementById('editarArquivado').checked = documento.status === 'ARQUIVADO';

        document.getElementById('infoStatus').textContent = documento.status;
        document.getElementById('infoDataCriacao').textContent =
            new Date(documento.dataCriacao).toLocaleDateString();
        document.getElementById('infoNomeArquivo').textContent = documento.nomeArquivo || 'Documento';
    }

    async salvarEdicao() {
        const documentoId = document.getElementById('editarDocumentoId').value;
        const dados = this.obterDadosFormulario();

        if (!dados.titulo.trim()) {
            this.mostrarMensagem('Digite um título para o documento', 'erro');
            return;
        }

        const btnSalvar = this.form.querySelector('button[type="submit"]');
        const textoOriginal = btnSalvar.textContent;
        btnSalvar.disabled = true;
        btnSalvar.textContent = 'Salvando...';

        try {
            const formData = new FormData();
            formData.append('titulo', dados.titulo);

            if (dados.dataExpiracao) {
                const data = new Date(dados.dataExpiracao);
                if (!isNaN(data.getTime())) {
                    const dataMillis = data.getTime();
                    formData.append('dataExpiracao', dataMillis.toString());
                } else {
                    this.mostrarMensagem('Data de expiração inválida', 'erro');
                    return;
                }
            }

            console.log('Arquivado:', dados.arquivado);
            formData.append('arquivado', dados.arquivado);

            const response = await fetch(`/api/documentos/${documentoId}`, {
                method: 'PUT',
                body: formData
            });

            let resultado;
            try {
                resultado = await response.json();
            } catch (jsonError) {
                throw new Error('Resposta inválida do servidor');
            }

            if (response.ok) {
                this.mostrarMensagem('Documento atualizado com sucesso!', 'sucesso');
                this.fecharModal();
                this.carregarDocumentos();
            } else {
                this.mostrarMensagem(`Erro: ${resultado.detalhes || resultado.erro || 'Erro ao atualizar documento'}`, 'erro');
            }

        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        } finally {
            btnSalvar.disabled = false;
            btnSalvar.textContent = textoOriginal;
        }
    }

    obterDadosFormulario() {
        const dataExpiracao = document.getElementById('editarDataExpiracao').value;

        return {
            titulo: document.getElementById('editarTitulo').value,
            dataExpiracao: dataExpiracao ? dataExpiracao : null,
            arquivado: document.getElementById('editarArquivado').checked
        };
    }

    abrirModal() {
        this.modal.style.display = 'block';
    }

    fecharModal() {
        this.modal.style.display = 'none';
        this.form.reset();
    }

    mostrarMensagem(mensagem, tipo) {
        alert(`${tipo.toUpperCase()}: ${mensagem}`);
    }
}

class GerenciadorDocumentos {
    constructor() {
        this.container = document.querySelector('#documentos-content');
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
            this.gerenciadorCriacao = new GerenciadorCriacaoDocumentos();
        }
    }

    inicializarEdicao() {
        if (!this.gerenciadorEdicao) {
            this.gerenciadorEdicao = new GerenciadorEdicaoDocumentos();
        }
    }

    mudarParaTab(tabId) {
        this.gerenciadorTabs.mudarParaTab(tabId);
    }

    getTabAtual() {
        return this.gerenciadorTabs.getTabAtual();
    }
}

new GerenciadorDocumentos();
