document.addEventListener("DOMContentLoaded", () => {
    const contentDiv = document.querySelector("#content");
    const contentWrapper = document.querySelector("#content .wrapper");

    let botoesSecao = document.querySelectorAll(".secao-botao");

    async function carregarSecao(e) {
        let botaoSecaoClicado = e.currentTarget;
        let secaoId = botaoSecaoClicado.dataset.id;
        contentDiv.classList = "";
        contentDiv.classList.add(secaoId)

        botoesSecao.forEach(botaoSecao => {
            if(botaoSecao != botaoSecaoClicado) 
                botaoSecao.classList.remove("ativo");
        })

        botaoSecaoClicado.classList.add("ativo");

        try {
            const response = await fetch(`/home/section?id=${secaoId}`);
            const data = await response.json();
            
            // HTML
            const htmlResponse = await fetch(data.html);
            const html = await htmlResponse.text();
            contentWrapper.innerHTML = html;

            // CSS dinâmico
            data.css.forEach(c => {
                if (!document.querySelector(`link[href="${c}"]`)) {
                    const link = document.createElement("link");
                    link.rel = "stylesheet";
                    link.href = c;
                    link.classList.add("css-dinamico");
                    document.querySelector("head").appendChild(link);
                }
            });

            // JS dinâmico
            data.js.forEach(j => {
                if (!document.querySelector(`script[src="${j}"]`)) {
                    const script = document.createElement("script");
                    script.src = j;
                    script.classList.add("js-dinamico");
                    script.defer = true;
                    document.body.appendChild(script);
                }
            });

        } catch (error) {
            console.error('Erro ao carregar seção:', error);
        }
    }
    
    botoesSecao.forEach(botaoSecao => {
        botaoSecao.addEventListener("click", carregarSecao);
    })
});