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
            if (botaoSecao != botaoSecaoClicado)
                botaoSecao.classList.remove("ativo");
        })

        botaoSecaoClicado.classList.add("ativo");

        try {
            const response = await fetch(`/home/section?id=${secaoId}`, {
                cache: "no-store"
            });
            const data = await response.json();

            // HTML
            const htmlResponse = await fetch(`${data.html}?v=${Date.now()}`, {
                cache: "no-store"
            });
            const html = await htmlResponse.text();

            const secoesConteudoList = contentWrapper.querySelectorAll(".secao-conteudo");
            secoesConteudoList.forEach(secaoConteudo => {
                secaoConteudo.classList.add("invisivel");
            });

            const secaoConteudo = contentWrapper.querySelector(`#${secaoId}-content`);
            if (!secaoConteudo) {
                let secaoDiv = document.createElement("div");
                secaoDiv.id = `${secaoId}-content`;
                secaoDiv.classList.add("secao-conteudo");

                secaoDiv.innerHTML = html;

                contentWrapper.appendChild(secaoDiv);

                // CSS dinâmico
                data.css.forEach(c => {
                    if (!document.querySelector(`link[href="${c}"]`)) {
                        const link = document.createElement("link");
                        link.rel = "stylesheet";
                        link.href = `${c}?v=${Date.now()}`;
                        link.classList.add("css-dinamico");
                        document.querySelector("head").appendChild(link);
                    }
                });

                // JS dinâmico
                async function carregarScripts(data) {
                    for (const j of data) {
                        if (!document.querySelector(`script[src="${j}"]`)) {
                            await new Promise((resolve, reject) => {
                                const script = document.createElement("script");
                                script.src = `${j}?v=${Date.now()}`;
                                script.classList.add("js-dinamico");
                                script.defer = true;

                                script.onload = () => resolve();
                                script.onerror = () => reject();

                                document.body.appendChild(script);
                            });
                        }
                    }
                }

                await carregarScripts(data.js);
            }
            else {
                secaoConteudo.classList.remove("invisivel");
            }
        } catch (error) {
            console.error('Erro ao carregar seção:', error);
        }
    }

    botoesSecao.forEach(botaoSecao => {
        botaoSecao.addEventListener("click", carregarSecao);
        if (botaoSecao.dataset.default == "true")
            botaoSecao.click();
    })
});