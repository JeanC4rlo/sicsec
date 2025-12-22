class GerenciadorCriacaoLivros {
    constructor() {
        this.form = document.getElementById('criarLivrosForm');
        this.init();
    }

    init() {
        this.tituloInput = document.getElementById('titulo');
        this.autorInput = document.getElementById('autor');
        this.editoraInput = document.getElementById('editor');
        this.isbnInput = document.getElementById('isbn');
        this.anoInput = document.getElementById('ano');
        this.btnEnviar = this.form.querySelector('button[type="submit"]');

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.criarLivro();
        });
    }

    async criarLivro() {
        const titulo = this.tituloInput.value.trim();
        const autor = this.autorInput.value.trim();
        const editora = this.editoraInput.value.trim();
        const isbn = this.isbnInput.value.trim();
        const ano = this.anoInput.value.trim();

        if (!titulo || !autor || !editora || !isbn || !ano) {
            this.mostrarMensagem('Preencha todos os campos', 'erro');
            return;
        }

        if (!/^\d{10}|\d{13}$/.test(isbn)) {
            this.mostrarMensagem('ISBN inválido', 'erro');
            return;
        }

        if (isNaN(ano) || ano.length < 1 || !Number.isInteger(ano)) {
            this.mostrarMensagem('Ano inválido', 'erro');
            return;
        }

        this.btnEnviar.disabled = true;
        this.btnEnviar.textContent = 'Salvando...';

        try {
            const formData = new FormData();
            formData.append('titulo', titulo);
            formData.append('autor', autor);
            formData.append('editora', editora);
            formData.append('isbn', isbn);
            formData.append('ano', ano);

            const response = await fetch('/api/livros/criar', {
                method: 'POST',
                body: formData
            });

            const resultado = await response.json();

            if (response.ok) {
                this.mostrarMensagem('Livro cadastrado com sucesso!', 'sucesso');
                this.limparFormulario();
            } else {
                this.mostrarMensagem(resultado.erro || 'Erro ao cadastrar livro', 'erro');
            }

        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        } finally {
            this.btnEnviar.disabled = false;
            this.btnEnviar.textContent = 'Criar Livro';
        }
    }


    mostrarMensagem(mensagem, tipo) {
        alert(`${tipo.toUpperCase()}: ${mensagem}`);
    }

    limparFormulario() {
        this.form.reset();
    }
}

class GerenciadorEdicaoLivros {
    constructor() {
        this.modal = document.getElementById('modalEditarLivro');
        this.form = document.getElementById('formEditarLivro');
        this.listaLivros = document.getElementById('listaLivros');
        this.livroAtual = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.carregarLivros();
    }

    setupEventListeners() {
        document.getElementById('buscarLivro').addEventListener('input', () => {
            this.carregarLivros();
        });

        document.getElementById('filtroStatus').addEventListener('change', () => {
            this.carregarLivros();
        });

        this.form.querySelector('.btn-cancelar').addEventListener('click', () => {
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

    async carregarLivros() {
        const busca = document.getElementById('buscarLivro').value;
        const status = document.getElementById('filtroStatus').value;

        try {
            const response = await fetch(`/api/livros/listar?busca=${encodeURIComponent(busca)}&status=${status}`);
            if (!response.ok) throw new Error('Erro ao carregar livros');
            const livros = await response.json();
            this.mostrarLivros(livros);
        } catch (error) {
            console.error('Erro ao carregar livros:', error);
            this.mostrarMensagem('Erro ao carregar livros', 'erro');
        }
    }

    mostrarLivros(livros) {
        if (!livros.length) {
            this.listaLivros.innerHTML = `
                <div class="livro-vazio">
                    <p>Nenhum livro encontrado</p>
                </div>
            `;
            return;
        }

        this.listaLivros.innerHTML = livros.map(livro => `
            <div class="livro-item" data-livro-id="${livro.id}">
                <div class="livro-info">
                    <div class="livro-meta">
                        <h4>${livro.titulo}</h4>
                        <span class="livro-status status-${livro.status.toLowerCase()}">${livro.status}</span>
                    </div>
                    <p>Código: ${livro.codigo}</p>
                    <p>Autor: ${livro.autor}</p>
                    <p>Editora: ${livro.editora}</p>
                    <p>ISBN: ${livro.isbn}</p>
                    <p>Ano: ${livro.ano}</p>
                </div>
                <div class="livro-acoes">
                    <button type="button" class="btn-editar">Editar</button>
                </div>
            </div>
        `).join('');

        this.listaLivros.querySelectorAll('.btn-editar').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                const livroId = btn.closest('.livro-item').dataset.livroId;
                this.abrirEdicao(livroId);
            });
        });
    }

    async abrirEdicao(livroId) {
        try {
            const response = await fetch(`/api/livros/${livroId}`);
            if (!response.ok) throw new Error('Livro não encontrado');
            this.livroAtual = await response.json();
            this.preencherFormulario(this.livroAtual);
            this.abrirModal();
        } catch (error) {
            console.error('Erro ao carregar livro:', error);
            this.mostrarMensagem('Erro ao carregar livro', 'erro');
        }
    }

    preencherFormulario(livro) {
        document.getElementById('editarLivroId').value = livro.id;
        document.getElementById('editarTitulo').value = livro.titulo;
        document.getElementById('editarAutor').value = livro.autor;
        document.getElementById('editarEditor').value = livro.editora;
        document.getElementById('editarISBN').value = livro.isbn;
        document.getElementById('editarAno').value = livro.ano;
        
        if(livro.status == "DISPONIVEL" || livro.status == "EMPRESTADO")
            document.getElementById("editarDisponivel").checked = true;
        else
            document.getElementById("editarDisponivel").checked = false;
    }

    async salvarEdicao() {
        const livroId = document.getElementById('editarLivroId').value;

        const dados = {
            titulo: document.getElementById('editarTitulo').value.trim(),
            autor: document.getElementById('editarAutor').value.trim(),
            editora: document.getElementById('editarEditor').value.trim(),
            isbn: document.getElementById('editarISBN').value.trim(),
            ano: document.getElementById('editarAno').value.trim(),
            disponivel: document.getElementById('editarDisponivel').checked
        };

        if (!dados.titulo || !dados.autor || !dados.editora || !dados.isbn || !dados.ano) {
            this.mostrarMensagem('Preencha todos os campos', 'erro');
            return;
        }

        const btnSalvar = this.form.querySelector('button[type="submit"]');
        const textoOriginal = btnSalvar.textContent;
        btnSalvar.disabled = true;
        btnSalvar.textContent = 'Salvando...';

        try {
            const formData = new FormData();
            formData.append('titulo', dados.titulo);
            formData.append('autor', dados.autor);
            formData.append('editora', dados.editora);
            formData.append('isbn', dados.isbn);
            formData.append('ano', dados.ano);
            formData.append('disponivel', dados.disponivel);

            const response = await fetch(`/api/livros/${livroId}`, {
                method: 'PUT',
                body: formData
            });

            const resultado = await response.json();

            if (response.ok) {
                this.mostrarMensagem('Livro atualizado com sucesso!', 'sucesso');
                this.fecharModal();
                this.carregarLivros();
            } else {
                this.mostrarMensagem(resultado.erro || 'Erro ao atualizar livro', 'erro');
            }
        } catch (error) {
            this.mostrarMensagem('Erro de conexão: ' + error.message, 'erro');
        } finally {
            btnSalvar.disabled = false;
            btnSalvar.textContent = textoOriginal;
        }
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

class GerenciadorLivros {
    constructor() {
        this.container = document.querySelector('#livros-content');
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
            this.gerenciadorCriacao = new GerenciadorCriacaoLivros();
        }
    }

    inicializarEdicao() {
        if (!this.gerenciadorEdicao) {
            this.gerenciadorEdicao = new GerenciadorEdicaoLivros();
        }
    }

    mudarParaTab(tabId) {
        this.gerenciadorTabs.mudarParaTab(tabId);
    }

    getTabAtual() {
        return this.gerenciadorTabs.getTabAtual();
    }
}

new GerenciadorLivros();
