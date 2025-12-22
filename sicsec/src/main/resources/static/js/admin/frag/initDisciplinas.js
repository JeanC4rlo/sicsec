function switchTab(tab) {
    const tabs = document.querySelectorAll('.disciplinas .tab');
    const buttons = document.querySelectorAll('.disciplinas header button');
    
    tabs.forEach(t => t.classList.remove('ativo'));
    buttons.forEach(b => b.classList.remove('ativo'));

    if (tab === 'cadastrar') {
        document.getElementById('tabCadastrar').classList.add('ativo');
        document.getElementById('btnTabCadastrar').classList.add('ativo');
    } else {
        document.getElementById('tabEditar').classList.add('ativo');
        document.getElementById('btnTabEditar').classList.add('ativo');
    }
}

async function salvarDisciplinaNoServidor(dados) {
    const response = await fetch('/api/disciplina/salvar', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(dados)
    });

    if (!response.ok) throw new Error('Erro ao salvar');
    return await response.text();
}

async function atualizarDisciplinaNoServidor(dados) {
    const response = await fetch('/api/disciplina/atualizar', {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(dados)
    });

    if (!response.ok) throw new Error('Erro ao atualizar');
    return await response.text();
}

async function preencherFormularioEdicao(codigo, instances) {
    try {
        const response = await fetch(`/api/disciplina/${codigo}`);

        if (!response.ok) {
            throw new Error("Disciplina não encontrada");
        }

        const disciplina = await response.json();

        document.getElementById('editNome').value = disciplina.nome;
        document.getElementById('editCodigo').value = disciplina.codigo;
        document.getElementById('editCarga').value = disciplina.cargaHoraria;
        document.getElementById('editBase').value = disciplina.modalidade || "";

        instances.profEdit.removeActiveItems();
        if (disciplina.professores) {
            const idsProfs = disciplina.professores.map(p => p.id);
            instances.profEdit.setChoiceByValue(idsProfs);
        }

        instances.aluEdit.removeActiveItems();
        if (disciplina.alunos) {
            const idsAlunos = disciplina.alunos.map(a => a.id);
            instances.aluEdit.setChoiceByValue(idsAlunos);
        }

        document.getElementById('editarDisciplinaForm').style.display = 'block';
    } catch (error) {
        console.error("Erro ao buscar disciplina:", error);
        alert("Erro ao buscar dados: " + error.message);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const configChoices = {
        removeItemButton: true,
        searchEnabled: true,
        noResultsText: 'Não encontrado',
        itemSelectText: 'Selecionar',
        placeholderValue: 'Selecione...'
    };

    const instances = {
        profCad: new Choices('#selectProfessoresCad', configChoices),
        aluCad: new Choices('#selectAlunosCad', configChoices),
        busca: new Choices('#buscaDisciplina', configChoices),
        profEdit: new Choices('#editProfessores', configChoices),
        aluEdit: new Choices('#editAlunos', configChoices)
    };

    const tabButtons = document.querySelectorAll('header button[data-tab]');
    const tabs = document.querySelectorAll('.tab');

    tabButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const target = btn.getAttribute('data-tab');
            tabButtons.forEach(b => b.classList.remove('ativo'));
            tabs.forEach(t => t.classList.remove('ativo'));

            btn.classList.add('ativo');
            document.querySelector(`.${target}`).classList.add('ativo');
        });
    });

    const formCad = document.getElementById('cadastrarDisciplinaForm');
    if (formCad) {
        formCad.addEventListener('submit', async (e) => {
            e.preventDefault();
            const dados = {
                id: document.getElementById('disciplinaId').value,
                nome: document.getElementById('nomeDisciplina').value,
                codigo: document.getElementById('codigoDisciplina').value,
                cargaHoraria: parseInt(document.getElementById('cargaHoraria').value),
                modalidade: document.getElementById('baseCurricular').value,
                professorIds: instances.profCad.getValue(true),
                alunoIds: instances.aluCad.getValue(true) 
            };
            
            try {
                const mensagemSucesso = await salvarDisciplinaNoServidor(dados);
                
                alert(mensagemSucesso); 
                
                formCad.reset();
                instances.profCad.removeActiveItems();
                instances.aluCad.removeActiveItems();
            } catch (error) {
                alert("Erro ao cadastrar:" + error.message);
            }
        });
    }

    const formBusca = document.getElementById('buscarDisciplinaForm');

    if (formBusca) {
        formBusca.addEventListener('submit', (e) => {
            e.preventDefault();
            const codigo = instances.busca.getValue(true);

            if (codigo) {
                preencherFormularioEdicao(codigo, instances);
            } else {
                alert("Selecione ou digite um código para buscar");
            }
        });
    }
});