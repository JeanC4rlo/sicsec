function initGestao() {
    const botoes = document.querySelectorAll("#matricula header button");
    const abas = document.querySelectorAll(".tab");
    const abaCriar = document.querySelector('.criar');
    const abaEditar = document.querySelector('.editar');
    if (!abaCriar || !abaEditar) return;

    // Criação de matrícula
    const radios = abaCriar.querySelectorAll('.radio-group input');
    const existenteCampos = abaCriar.querySelector('.existente-campos');

    function atualizarCampos(radio) {
        radios.forEach(r => r.checked = (r === radio));
        existenteCampos.style.display = radio.value === 'existente' ? 'flex' : 'none';
    }

    radios.forEach(radio => {
        if (!radio.dataset.listener) {
            radio.addEventListener('change', e => atualizarCampos(e.currentTarget));
            radio.dataset.listener = 'true';
        }
    });

    botoes.forEach(btn => {
        btn.addEventListener("click", () => {
            const tab = btn.dataset.tab;
            abas.forEach(aba => aba.style.display = aba.classList.contains(tab) ? 'flex' : 'none');
            botoes.forEach(b => b.classList.remove('ativo'));
            btn.classList.add('ativo');

            if (tab === 'criar') {
                const defaultRadio = Array.from(radios).find(r => r.value === 'novo');
                if (defaultRadio) atualizarCampos(defaultRadio);
            }
        });
    });

    // Edição de matrícula
    const buscarForm = abaEditar.querySelector('#buscarMatriculaForm');
    const editarForm = abaEditar.querySelector('#editarForm');
    
    buscarForm.addEventListener('submit', async e => {
        e.preventDefault();
        const numero = abaEditar.querySelector('#numeroMatriculaBuscar').value;
        if (!numero) return;

        const resp = await fetch(`/api/matricula/${numero}`);
        if (!resp.ok) {
            alert('Matrícula não encontrada');
            return;
        }

        const data = await resp.json();

        abaEditar.querySelector('#numeroMatricula').value = data.numeroMatricula;
        abaEditar.querySelector('#nome').value = data.nome || '';
        abaEditar.querySelector('#email').value = data.email || '';
        abaEditar.querySelector('#telefone').value = data.telefone || '';

        editarForm.style.display = 'block';

        // opcional: troca automaticamente para a aba "editar"
        abas.forEach(aba => aba.style.display = aba.classList.contains('editar') ? 'flex' : 'none');
        botoes.forEach(b => b.classList.toggle('ativo', b.dataset.tab === 'editar'));
    });
}
