function initMatriculas() {
    const secao = document.querySelector("#matriculas");
    if (!secao) return;

    setupAbasMatriculas(secao);
    setupCursoSearch(secao);
    setupEditarMatricula(secao);
    setupBuscarMatricula(secao);
    setupRegistrarMatricula(secao);
}

function setupAbasMatriculas(secao) {
    const botoes = secao.querySelectorAll("header button");
    const abas = secao.querySelectorAll(".tab");
    const ultimaAba = localStorage.getItem("matriculasAbaAtiva") || "criar";

    const trocarAba = (tab) => {
        abas.forEach(aba => aba.style.display = aba.classList.contains(tab) ? "block" : "none");
        botoes.forEach(btn => btn.classList.toggle("ativo", btn.dataset.tab === tab));
        localStorage.setItem("matriculasAbaAtiva", tab);
    };

    botoes.forEach(btn => btn.addEventListener("click", () => trocarAba(btn.dataset.tab)));
    trocarAba(ultimaAba);
}

async function carregarCursos(cursoSelect, cursoIdSelecionado = null) {
    try {
        const response = await fetch('/api/curso/getAll', { method: 'POST' });
        if (!response.ok) throw new Error("Erro ao carregar cursos");

        const cursos = await response.json();
        cursoSelect.innerHTML = '<option value="" disabled hidden>Selecione um curso</option>';

        cursos.forEach(curso => {
            const option = document.createElement('option');
            option.value = curso.id;
            option.textContent = curso.nome;
            console.warn(curso.id);
            console.log(cursoIdSelecionado);
            if (cursoIdSelecionado && String(curso.id) === String(cursoIdSelecionado)) {
                option.selected = true;
            }
            cursoSelect.appendChild(option);
        });
    } catch (err) {
        console.error(err);
    }
}

function setupCursoSearch(secao) {
    const cursoSelect = secao.querySelector('select[name="cursoId"]');
    if (!cursoSelect) return;

    let resultsContainer = cursoSelect.nextElementSibling;
    if (!resultsContainer) {
        resultsContainer = document.createElement('div');
        resultsContainer.classList.add('curso-results');
        resultsContainer.style.display = 'none';
        cursoSelect.parentNode.appendChild(resultsContainer);
    }

    let debounceTimeout;
    cursoSelect.addEventListener('focus', () => {
        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(() => carregarCursos(cursoSelect), 200);
    });
}

function setupBuscarMatricula(secao) {
    const buscarForm = secao.querySelector("#buscarMatriculaForm");
    if (!buscarForm) return;

    const editarForm = secao.querySelector("#editarForm");
    if (!editarForm) return;

    buscarForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const numeroMatricula = buscarForm.querySelector("#numeroMatriculaBuscar").value.trim();
        if (!numeroMatricula) return;

        try {
            const response = await fetch(`/api/matricula/${numeroMatricula}`);
            if (!response.ok) throw new Error("Matrícula não encontrada");

            const matricula = await response.json();

            editarForm.querySelector('input[name="cpf"]').value = matricula.cpf.cpf;
            editarForm.querySelector('input[name="nome"]').value = matricula.nome;
            editarForm.querySelector('input[name="email"]').value = matricula.email;
            editarForm.querySelector('input[name="telefone"]').value = matricula.telefone;
            editarForm.querySelector('input[name="numeroMatriculaAnterior"]').value = matricula.numeroMatricula;
            editarForm.querySelector('input[name="numeroMatriculaNovo"]').value = matricula.numeroMatricula;

            const cursoSelect = editarForm.querySelector('select[name="cursoId"]');
            const cursoId = matricula.cursoId;
            
            if (cursoSelect) await carregarCursos(cursoSelect, cursoId);
            
            editarForm.style.display = "block";
        } catch (err) {
            console.error(err);
            alert("Erro ao buscar matrícula");
            editarForm.style.display = "none";
        }
    });
}


function setupEditarMatricula(secao) {
    const editarForm = secao.querySelector("#editarForm");
    if (!editarForm) return;

    editarForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const formData = new FormData(editarForm);

        const buscarForm = secao.querySelector("#buscarMatriculaForm");
        if (!buscarForm) return;

        try {
            const response = await fetch("/api/matricula/atualizar", {
                method: "POST",
                body: formData
            });

            if (!response.ok) throw new Error("Erro ao atualizar matrícula");

            alert("Matrícula atualizada com sucesso!");
        } catch (err) {
            console.error(err);
            alert("Erro ao atualizar matrícula");
        }
        finally {
            buscarForm.reset();
            editarForm.style.display = "none";
            editarForm.reset();
        }
    });
}

function setupRegistrarMatricula(secao) {
    const form = secao.querySelector("#formMatricula");
    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const formData = new FormData(form);

        try {
            const response = await fetch("/api/matricular", {
                method: "POST",
                body: formData
            });

            if (!response.ok) throw new Error("Erro ao cadastrar matrícula");

            alert("Matrícula cadastrada com sucesso!");

        } catch (err) {
            console.error(err);
            alert("Erro ao cadastrar matrícula");
        }
        finally {
            form.reset();
        }
    });
}

