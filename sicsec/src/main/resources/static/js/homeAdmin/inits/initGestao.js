function initGestao() {
  const secao = document.querySelector("#matricula");
  if (!secao) return;

  const botoes = secao.querySelectorAll("header button");
  const abas = secao.querySelectorAll(".tab");
  const abaCriar = secao.querySelector('.criar');
  const abaEditar = secao.querySelector('.editar');
  if (!abaCriar || !abaEditar) return;

  // Criação de matrícula
  const radios = abaCriar.querySelectorAll('.radio-group input');
  const existenteCampos = abaCriar.querySelector('.existente-campos');

  function atualizarCampos(radio) {
    radios.forEach(r => r.checked = (r === radio));
    if (existenteCampos) existenteCampos.style.display = radio.value === 'existente' ? 'flex' : 'none';
  }

  radios.forEach(radio => {
    if (!radio.dataset.listener) {
      radio.addEventListener('change', e => atualizarCampos(e.currentTarget));
      radio.dataset.listener = 'true';
    }
  });

  // usa class toggle em vez de style.display (evita inline styles que sobrescrevem CSS)
  botoes.forEach(btn => {
    if (!btn.dataset.listener) {
      btn.addEventListener("click", () => {
        const tab = btn.dataset.tab;
        abas.forEach(aba => aba.classList.toggle('ativo', aba.classList.contains(tab)));
        botoes.forEach(b => b.classList.toggle('ativo', b === btn));

        // se ativou a aba criar, garante estado default do radio
        if (tab === 'criar') {
          const defaultRadio = Array.from(radios).find(r => r.value === 'novo');
          if (defaultRadio) atualizarCampos(defaultRadio);
        }
      });
      btn.dataset.listener = 'true';
    }
  });

  // Exibe aba inicial se nenhuma estiver ativa
  if (!Array.from(abas).some(a => a.classList.contains('ativo'))) {
    const inicial = secao.querySelector('.criar');
    const btnInicial = secao.querySelector('header button[data-tab="criar"]');
    if (inicial && btnInicial) {
      inicial.classList.add('ativo');
      btnInicial.classList.add('ativo');
    }
  }

  // Edição de matrícula (escopada)
  const buscarForm = abaEditar.querySelector('#buscarMatriculaForm');
  const editarForm = abaEditar.querySelector('#editarForm');

  if (buscarForm) {
    if (!buscarForm.dataset.listener) {
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

        // mostrar edição via classe .ativo no wrapper da aba editar
        editarForm.style.display = 'block'; // esse display é interno ao mesmo bloco, ok
        abas.forEach(aba => aba.classList.toggle('ativo', aba.classList.contains('editar')));
        botoes.forEach(b => b.classList.toggle('ativo', b.dataset.tab === 'editar'));
      });
      buscarForm.dataset.listener = 'true';
    }
  }
}
