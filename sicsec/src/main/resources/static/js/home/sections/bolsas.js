function criarBolsa(bolsa, container) {
    const area = bolsa.area_conhecimento || {};

    const card = document.createElement("div");
    card.classList.add("bolsa-card");
    card.dataset.codigo = bolsa.codigo;
    card.innerHTML = `
        <h2>${bolsa.titulo || ""}</h2>
        <p><strong>Código:</strong> ${bolsa.codigo || ""}</p>
        <p><strong>Aluno/Discente:</strong> ${bolsa.discente || ""}</p>
        <p><strong>Orientador:</strong> ${bolsa.orientador || ""}</p>
        <p><strong>Centro:</strong> ${bolsa.centro || ""}</p>
        <p><strong>Departamento:</strong> ${bolsa.departamento || ""}</p>
        <p><strong>Tipo de Bolsa:</strong> ${bolsa.tipo_bolsa || ""}</p>
        <p><strong>Área de Conhecimento:</strong> 
            ${area.grande_area || ""} - ${area.area || ""} - ${area.subarea || ""} ${area.especialidade ? "- " + area.especialidade : ""}
        </p>
        <div class="informacao-adicional">
            <div class="status">
                <span class="status-indicador ${formatarClasseStatus(bolsa.status)}"></span>
                ${bolsa.status || ""}
            </div>
            <div class="acoes">
                <button class="ler-mais">
                    <img src="../imagens/home/artigo.svg">
                    Ver mais
                </button>
                <button class="interesse">
                    <img src="../imagens/home/check.svg">
                    ${bolsa.registrada ? "Registrado" : "Registrar interesse"}
                </button>
            </div>
        </div>
    `;
    container.appendChild(card);

    // Configurar evento do botão "Ver mais"
    const btnLerMais = card.querySelector('.ler-mais');
    btnLerMais.addEventListener('click', () => {
        exibirDetalhesBolsa(bolsa);
    });

    // Configurar evento do botão "Registrar interesse"
    const btnInteresse = card.querySelector('.interesse');
    btnInteresse.addEventListener('click', () => {
        registrarInteresseBolsa(bolsa, btnInteresse);
    });
}

function formatarClasseStatus(status) {
    if (!status) return "";
    
    const s = status.toLowerCase();
    if (s.includes("conclu") || s.includes("final")) return "status-concluido";
    if (s.includes("andamento")) return "status-andamento";
    if (s.includes("cancel")) return "status-cancelado";
    return "status-desconhecido";
}

function exibirDetalhesBolsa(bolsa) {
    const area = bolsa.area_conhecimento || {};
    const containerMais = document.querySelector('#bolsas .mais');
    
    // Guardar qual aba estava ativa antes de abrir os detalhes
    const abaAnterior = document.querySelector('#bolsas .wrapper > div.ativo');
    
    // Esconder outras seções usando forEach
    document.querySelectorAll('#bolsas .wrapper > div').forEach(secao => {
        secao.classList.remove('ativo');
    });
    containerMais.classList.add('ativo');

    const objetivosEspecificos = bolsa.objetivos.especificos.map(obj => `<li>${obj}</li>`).join('');
    const referencias = bolsa.referencias.map(ref => `<li>${ref}</li>`).join('');

    containerMais.innerHTML = `
        <div class="detalhes-bolsa">
            <button class="voltar">
                <img src="../imagens/home/voltar.svg"> Voltar
            </button>
            <h2>${bolsa.titulo || ""}</h2>
            <p><strong>Código:</strong> ${bolsa.codigo || ""}</p>
            <p><strong>Aluno/Discente:</strong> ${bolsa.discente || ""}</p>
            <p><strong>Orientador:</strong> ${bolsa.orientador || ""}</p>
            <p><strong>Centro:</strong> ${bolsa.centro || ""}</p>
            <p><strong>Departamento:</strong> ${bolsa.departamento || ""}</p>
            <p><strong>Tipo de Bolsa:</strong> ${bolsa.tipo_bolsa || ""}</p>
            <p><strong>Status:</strong> ${bolsa.status || ""}</p>
            <p><strong>Edital:</strong> ${bolsa.edital || ""}</p>
            <p><strong>Cota:</strong> ${bolsa.cota || ""}</p>
            <p><strong>Área de Conhecimento:</strong> 
                ${area.grande_area || ""} - ${area.area || ""} - ${area.subarea || ""} ${area.especialidade ? "- " + area.especialidade : ""}
            </p>
            
            <h3>Introdução e Justificativa</h3>
            <p>${bolsa.introducao_justificativa || ""}</p>
            
            <h3>Objetivos</h3>
            <p><strong>Objetivo Geral:</strong> ${bolsa.objetivos.geral || ""}</p>
            <p><strong>Objetivos Específicos:</strong></p>
            <ul>${objetivosEspecificos}</ul>
            
            <h3>Metodologia</h3>
            <p>${bolsa.metodologia || ""}</p>
            
            <h3>Habilidades Adquiridas</h3>
            <p>${bolsa.habilidades_adquiridas || ""}</p>
            
            <h3>Referências</h3>
            <ul>${referencias}</ul>
            
            <button class="interesse-detalhes">
                <img src="../imagens/home/check.svg">
                ${bolsa.registrada ? "Registrado" : "Registrar interesse"}
            </button>
        </div>
    `;

    // Configurar evento do botão voltar
    const btnVoltar = containerMais.querySelector('.voltar');
    btnVoltar.addEventListener('click', () => {
        containerMais.classList.remove('ativo');
        if (abaAnterior) {
            abaAnterior.classList.add('ativo');
        }
    });

    // Configurar evento do botão de interesse na página de detalhes
    const btnInteresseDetalhes = containerMais.querySelector('.interesse-detalhes');
    btnInteresseDetalhes.addEventListener('click', () => {
        registrarInteresseBolsa(bolsa, btnInteresseDetalhes);
    });
}

function registrarInteresseBolsa(bolsa, botao) {
    bolsa.registrada = true;
    botao.innerHTML = `<img src="../imagens/home/check.svg"> Registrado`;
    botao.disabled = true;
    
    // Atualizar também no card original se existir
    const cardOriginal = document.querySelector(`.bolsa-card[data-codigo="${bolsa.codigo}"] .interesse`);
    if (cardOriginal) {
        cardOriginal.innerHTML = `<img src="../imagens/home/check.svg"> Registrado`;
        cardOriginal.disabled = true;
    }
    
    alert("Bolsa adicionada em 'Minhas bolsas'.");
}

function mostrarBolsas(lista, container) {
    container.innerHTML = "";
    if (!lista || lista.length === 0) {
        container.innerHTML = "<p>Nenhuma bolsa encontrada.</p>";
        return;
    }
    lista.forEach(bolsa => criarBolsa(bolsa, container));
}

function filtrarBolsas(campoPesquisa, textoPesquisa, bolsas) {
    const campo = campoPesquisa.value;
    const texto = textoPesquisa.value.toLowerCase().trim();
    if (!bolsas.length) return [];

    return bolsas.filter(bolsa => {
        if (!texto) return true;
        if (campo === "todos") {
            return Object.values(bolsa).some(valor => {
                if (typeof valor === "string") return valor.toLowerCase().includes(texto);
                if (typeof valor === "object" && valor !== null) {
                    return Object.values(valor).some(v => typeof v === "string" ? v.toLowerCase().includes(texto) : false);
                }
                return false;
            });
        } else {
            let valorCampo = bolsa[campo];
            if (typeof valorCampo === "object" && valorCampo !== null) valorCampo = Object.values(valorCampo).join(" ");
            return valorCampo && valorCampo.toLowerCase().includes(texto);
        }
    });
}

function carregarBolsas(url, callback) {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            const bolsas = data.map(b => ({ ...b, registrada: false }));
            callback(bolsas);
        })
        .catch(err => console.error(err));
}

function configurarNavegacaoAbas(bolsas) {
    const botoes = document.querySelectorAll('#bolsas header button');
    const secoes = document.querySelectorAll('#bolsas .wrapper > div');

    function mostrarAba(aba) {
        // Remove 'ativo' de todos os botões e seções
        botoes.forEach(btn => btn.classList.remove('ativo'));
        secoes.forEach(sec => sec.classList.remove('ativo'));

        // Adiciona 'ativo' no botão e seção correspondentes
        const btnAtivo = document.querySelector(`#bolsas header button[data-tab="${aba}"]`);
        const secAtiva = document.querySelector(`#bolsas .${aba}`);
        
        if (btnAtivo) btnAtivo.classList.add('ativo');
        if (secAtiva) secAtiva.classList.add('ativo');

        // Se for a aba "minhas-bolsas", carrega as bolsas registradas
        if (aba === 'minhas-bolsas') {
            const minhasBolsas = bolsas.filter(b => b.registrada);
            mostrarBolsas(minhasBolsas, secAtiva);
        }
    }

    botoes.forEach(btn => {
        btn.addEventListener('click', () => mostrarAba(btn.dataset.tab));
    });

    // Iniciar com a aba de pesquisa
    mostrarAba('pesquisa');
}

function initBolsas() {
    const containerPesquisa = document.querySelector("#bolsas .resultado");
    const campoPesquisa = document.getElementById("campoPesquisa");
    const textoPesquisa = document.getElementById("textoPesquisa");
    const btnPesquisar = document.getElementById("btnPesquisar");

    carregarBolsas("../data/bolsas.json", bolsas => {
        mostrarBolsas(bolsas, containerPesquisa);
        configurarNavegacaoAbas(bolsas);
        
        btnPesquisar.addEventListener("click", () => {
            const filtradas = filtrarBolsas(campoPesquisa, textoPesquisa, bolsas);
            mostrarBolsas(filtradas, containerPesquisa);
        });
    });
}