class GerenciadorAssinaturas {
    constructor() {
        this.container = document.querySelector('#assinaturas-content');
        this.containerAssinaturas = this.container.querySelector('.assinaturas');
        this.init();
    }

    init() {
        this.setupAssinaturas(this.containerAssinaturas);
    }

    setupAssinaturas(container) {
        fetch('/api/assinatura/listar')
            .then(response => response.json())
            .then(assinaturas => {
                if (assinaturas.length === 0) {
                    container.innerHTML = '<p>Não há assinaturas pendentes.</p>';
                    return;
                }

                assinaturas.forEach(assinatura => {
                    const assinaturaDiv = document.createElement('div');
                    assinaturaDiv.classList.add('assinatura-item');

                    assinaturaDiv.innerHTML = `
                        <h4>${assinatura.documento.titulo}</h4>
                        <div class="assinatura-meta">
                            <span class="assinatura-status status-${assinatura.status.toLowerCase()}">${assinatura.status}</span>
                            <span>Criado: ${new Date(assinatura.dataCriacao).toLocaleDateString()}</span>
                            ${assinatura.documento.dataExpiracao ? `<span>Expira: ${new Date(assinatura.documento.dataExpiracao).toLocaleDateString()}</span>` : ''}
                        </div>
                        <div class="assinatura-acoes"></div>
                    `;

                    container.appendChild(assinaturaDiv);
                    const assinaturaAcoesDiv = assinaturaDiv.querySelector('.assinatura-acoes');

                    const btnDownload = document.createElement('button');
                    btnDownload.classList.add('btn-acao');
                    btnDownload.classList.add('btn-download');
                    btnDownload.innerHTML = `
                            <img src="/images/icons/download.svg" alt="Download Icon">
                        `;
                    btnDownload.addEventListener('click', () => {
                        const link = document.createElement('a');
                        link.href = assinatura.documento.conteudo;
                        link.download = "";
                        link.click();
                    });

                    assinaturaAcoesDiv.appendChild(btnDownload);

                    if (assinatura.status === 'PENDENTE' || assinatura.status === 'ATRASADA') {

                        const btnAssinar = document.createElement('button');
                        btnAssinar.classList.add('btn-acao');
                        btnAssinar.classList.add('btn-assinar');
                        btnAssinar.textContent = 'Assinar';
                        btnAssinar.addEventListener('click', () => {
                            // Chamada à API para assinar
                            fetch(`/api/assinatura/assinar?assinaturaId=${assinatura.id}`)
                                .then(res => res.json())
                                .then(data => {
                                    assinaturaDiv.querySelector('.assinatura-status').textContent = `${data.status}`;
                                    btnAssinar.remove();
                                    btnRejeitar.remove();
                                });
                        });

                        const btnRejeitar = document.createElement('button');
                        btnRejeitar.classList.add('btn-acao');
                        btnRejeitar.classList.add('btn-rejeitar');
                        btnRejeitar.textContent = 'Rejeitar';
                        btnRejeitar.addEventListener('click', () => {
                            // Chamada à API para rejeitar
                            fetch(`/api/assinatura/rejeitar?assinaturaId=${assinatura.id}`)
                                .then(res => res.json())
                                .then(data => {
                                    assinaturaDiv.querySelector('.assinatura-acoes').textContent = `Status: ${data.status}`;
                                    btnAssinar.remove();
                                    btnRejeitar.remove();
                                });
                        });

                        assinaturaAcoesDiv.appendChild(btnAssinar);
                        assinaturaAcoesDiv.appendChild(btnRejeitar);
                    }
                });

            })
            .catch(error => {
                console.error('Erro ao carregar assinaturas:', error);
                container.innerHTML = '<p>Erro ao carregar assinaturas.</p>';
            });
    }
}

new GerenciadorAssinaturas();