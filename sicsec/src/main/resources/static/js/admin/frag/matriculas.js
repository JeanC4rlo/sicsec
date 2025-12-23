class GerenciadorMatriculas {
    constructor() {
        this.container = document.querySelector('#matriculas-content');
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

(function initMatriculas() {
    const secao = document.querySelector("#matriculas-content");
    if (!secao) return;

    setupAbasMatriculas(secao);
    setupCursoSearch(secao);
    setupBuscarMatricula(secao);
    setupEditarMatricula(secao);
    setupRegistrarMatricula(secao);

    function setupAbasMatriculas(secao) {
        const botoes = secao.querySelectorAll(".wrapper header button");
        const abas = secao.querySelectorAll(".tab");
        const ultimaAba = localStorage.getItem("matriculasAbaAtiva") || "criar";

        const trocarAba = (tab) => {
            abas.forEach(aba => aba.classList.toggle("ativo", aba.classList.contains(tab)));
            botoes.forEach(btn => btn.classList.toggle("ativo", btn.dataset.tab === tab));
            localStorage.setItem("matriculasAbaAtiva", tab);
        };

        botoes.forEach(btn => {
            btn.addEventListener("click", () => trocarAba(btn.dataset.tab));
        });

        trocarAba(ultimaAba);
    }

    async function carregarCursos(selectElement, cursoIdSelecionado = null) {
        try {
            const response = await fetch('/api/curso/getAll', { method: 'POST' });
            if (!response.ok) throw new Error("Erro ao carregar cursos");

            const cursos = await response.json();
            selectElement.innerHTML = '<option value="" disabled selected>Selecione um curso</option>';

            cursos.forEach(curso => {
                const option = document.createElement('option');
                option.value = curso.id;
                option.textContent = curso.nome;
                if (cursoIdSelecionado && String(curso.id) === String(cursoIdSelecionado)) {
                    option.selected = true;
                }
                selectElement.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar cursos:', error);
            selectElement.innerHTML = '<option value="" disabled selected>Erro ao carregar cursos</option>';
        }
    }

    function setupCursoSearch(secao) {
        const cursoSelect = secao.querySelector('select[name="cursoId"]');
        if (!cursoSelect) return;

        cursoSelect.addEventListener('focus', () => {
            if (cursoSelect.children.length <= 1) {
                carregarCursos(cursoSelect);
            }
        });
    }

    function setupBuscarMatricula(secao) {
        const buscarForm = secao.querySelector("#buscarMatriculaForm");
        const editarForm = secao.querySelector("#editarForm");

        if (!buscarForm || !editarForm) return;

        buscarForm.addEventListener("submit", async (event) => {
            event.preventDefault();

            const numeroMatricula = buscarForm.querySelector("#numeroMatriculaBuscar").value.trim();
            if (!numeroMatricula) return;

            try {
                const response = await fetch(`/api/matricula/${numeroMatricula}`);

                if (!response.ok) throw new Error("Matrícula não encontrada");

                const matricula = await response.json();
                console.log("Matrícula encontrada:", matricula);
                preencherFormularioEdicao(editarForm, matricula);

            } catch (error) {
                console.error('Erro ao buscar matrícula:', error);
                alert("Erro ao buscar matrícula");
                editarForm.style.display = "none";
            }
        });
    }

    function preencherFormularioEdicao(form, matricula) {
        form.querySelector('input[name="cpf"]').value = matricula.cpf?.cpf || '';
        form.querySelector('input[name="nome"]').value = matricula.nome || '';
        form.querySelector('input[name="email"]').value = matricula.email || '';
        form.querySelector('input[name="telefone"]').value = matricula.telefone || '';
        form.querySelector('input[name="numeroMatriculaAnterior"]').value = matricula.numeroMatricula || '';
        form.querySelector('input[name="numeroMatriculaNovo"]').value = matricula.numeroMatricula || '';

        const cursoSelect = form.querySelector('select[name="cursoId"]');
        console.log(matricula.cursoId);
        
        if (cursoSelect && matricula.cursoId) {
            carregarCursos(cursoSelect, matricula.cursoId);
            console.log("Curso selecionado:", matricula.cursoId);
        }
        else {
            carregarCursos(cursoSelect);
        }

        form.style.display = "block";
    }

    function setupEditarMatricula(secao) {
        const editarForm = secao.querySelector("#editarForm");
        const buscarForm = secao.querySelector("#buscarMatriculaForm");

        if (!editarForm || !buscarForm) return;

        editarForm.addEventListener("submit", async (event) => {
            event.preventDefault();

            try {
                const formData = new FormData(editarForm);
                const response = await fetch("/api/matricula/atualizar", {
                    method: "POST",
                    body: formData
                });

                if (!response.ok) throw new Error("Erro ao atualizar matrícula");

                alert("Matrícula atualizada com sucesso!");

            } catch (error) {
                console.error('Erro ao atualizar matrícula:', error);
                alert("Erro ao atualizar matrícula");
            } finally {
                buscarForm.reset();
                editarForm.style.display = "none";
                editarForm.reset();
            }
        });
    }

    function setupRegistrarMatricula(secao) {
        const form = secao.querySelector("#formMatricula");
        if (!form) return;

        form.addEventListener("submit", async (event) => {
            event.preventDefault();

            try {
                const formData = new FormData(form);
                const response = await fetch("/api/matricular", {
                    method: "POST",
                    body: formData
                });

                if (!response.ok) throw new Error("Erro ao cadastrar matrícula");

                alert("Matrícula cadastrada com sucesso!");

            } catch (error) {
                console.error('Erro ao cadastrar matrícula:', error);
                alert("Erro ao cadastrar matrícula");
            } finally {
                form.reset();
            }
        });
    }
})();