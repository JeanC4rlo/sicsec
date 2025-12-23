const bimestres = ["primeiro", "segundo", "terceiro", "quarto"];
const tooltip = document.querySelector(`#tooltip`);

async function mostrarTooltip(event, bimestreEl) {

    const alvo = event.target.closest(".bimestre-td");
    if (!alvo) return;

    if (tooltip.classList.contains("invisivel"))
        tooltip.classList.remove("invisivel");

    // posição do mouse
    tooltip.style.left = event.pageX + 10 + "px";
    tooltip.style.top = event.pageY + 10 + "px";

    tooltip.innerHTML = "";
    
    bimestreEl.notas.forEach(nota => {
        
        let p = document.createElement("p");
        p.innerHTML = "<b>" + nota.avaliacao + "</b><br/>Nota: " + nota.valor + (nota.valor > 1 ? " pontos." : " ponto.");
        tooltip.appendChild(p);

    });

}

async function moverTooltip(event, bimestreEl) {
    
    if (!tooltip.classList.contains("escondido")) {
        tooltip.style.left = event.pageX + 10 + "px";
        tooltip.style.top = event.pageY + 10 + "px";
    }
}

async function esconderTooltip(event, bimestreEl) {
    
    const alvo = event.target.closest(".bimestre-td")
        tooltip.classList.add("invisivel");

}

async function initSectionBoletim() {

    const notas = await fetchJSON("/api/boletim/aluno/acesso");
    console.log(notas);

    function preencherTabela() {

        const tabela = document.getElementById("tabelaBoletim");
        const tbody = tabela.querySelector("tbody");

        tbody.innerHTML = "";

        notas[0].componentes.forEach(componente => {
            const tr = document.createElement("tr");

            const tdDisciplina = document.createElement("td");
            tdDisciplina.textContent = componente.disciplina;
            tr.appendChild(tdDisciplina);

            let [td1Bi, td2Bi, td3Bi, td4Bi] = [null, null, null, null];
            let tdBis = [td1Bi, td2Bi, td3Bi, td4Bi];

            tdBis.forEach((_, i) => {
                tdBis[i] = document.createElement("td");
                tdBis[i].classList = "bimestre-td";
                tdBis[i].id = bimestres[i]+"-bimestre-td";
                tdBis[i].nota = 0;
                tdBis[i].bimestre = i+1;
                tdBis[i].notas = [];
            });
            
            componente.notas.forEach(nota => {
                switch(nota.bimestre) {

                    case "PRIMEIRO":
                        tdBis[0].nota += nota.valor;
                        tdBis[0].notas.push(nota);
                        break;
                    case "SEGUNDO":
                        tdBis[1].nota += nota.valor;
                        tdBis[1].notas.push(nota);
                        break;
                    case "TERCEIRO":
                        tdBis[2].nota += nota.valor;
                        tdBis[2].notas.push(nota);
                        break;
                    case "QUARTO":
                        tdBis[3].nota += nota.valor;
                        tdBis[3].notas.push(nota);
                        break;

                }
            });

            tdBis.forEach((_, i) => {

                tdBis[i].textContent = tdBis[i].nota;
                tdBis[i].addEventListener("mouseover", async (e) => mostrarTooltip(e, tdBis[i]));
                tdBis[i].addEventListener("mousemove", async (e) => moverTooltip(e, tdBis[i]));
                tdBis[i].addEventListener("mouseout", async (e) => esconderTooltip(e, tdBis[i]));
                tr.appendChild(tdBis[i]);

            });

            console.log(tdBis);

            const tdFaltas = document.createElement("td");
            tdFaltas.textContent = componente.faltas;
            tr.appendChild(tdFaltas);

            const tdNotaFinal = document.createElement("td");
            tdNotaFinal.textContent = componente.notaFinal;
            tr.appendChild(tdNotaFinal);

            const tdSituacao = document.createElement("td");
            tdSituacao.textContent = componente.situacao;
            tr.appendChild(tdSituacao);

            tbody.appendChild(tr);
            
        });
    }

    function initTabela() {
        const botaoBoletim = document.querySelector("button[data-id='boletim']");

        let anoAtual = new Date().getFullYear();

        botaoBoletim.addEventListener('click', () => preencherTabela());
        preencherTabela();
    }

    initTabela();

}

async function fetchJSON(url, options = { method: "POST" }) {
  const res = await fetch(url, options);
  if (!res.ok) throw new Error(`Erro HTTP ${res.status} em ${url}`);
  return res.json();
}

initSectionBoletim();
document.querySelector("button[data-id='boletim']").addEventListener("click", initSectionBoletim);
