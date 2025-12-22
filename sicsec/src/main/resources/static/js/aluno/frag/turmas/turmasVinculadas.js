const fotoPadrao = "/images/foto-padrao.png";

async function carregarPrincipal(turmaId) {

    const turma = await fetchJSON("/api/aluno/acesso/turma/info/" + encodeURIComponent(turmaId));

    document.querySelector("#cabecalho-turma #titulo-turma").textContent = turma.nome;
    document.querySelector("#cabecalho-turma #curso-turma").textContent = `Curso: ${turma.curso}`;
    document.querySelector("#cabecalho-turma #disciplina-turma").textContent = `Disciplina: ${turma.disciplina}`;

    const aulasEl = document.querySelector(".info-turma #aulas");
    const ultimaNoticiaEl = document.querySelector(".info-turma #ultima-noticia");
    const proximoCronogramaEl = document.querySelector(".info-turma #proximo-cronograma");

    aulasEl.innerHTML = "";
    ultimaNoticiaEl.innerHTML = "";
    proximoCronogramaEl.innerHTML = "";

    const h = document.createElement("h2");
    h.textContent = `Aulas.`;
    aulasEl.appendChild(h);

    if (turma.aulas)
    turma.aulas.forEach(aula => {
        
        const p = document.createElement("p");
        p.textContent = `${aula.diaSemana.replace("_", " ")} - ${aula.horarioInicio} às ${aula.horarioFim} - ${aula.sala}`;
        aulasEl.appendChild(p);
    });
    else {
        const p = document.createElement("p");
        p.textContent = "À definir."
        aulasEl.appendChild(p);
    }

    ultimaNoticiaEl.innerHTML = turma.ultimaNoticia ? `
        <h2>Notícia</h2>
        <div>
        <h3>${turma.ultimaNoticia.manchete}</h3>
        <p>${new Date(turma.ultimaNoticia.data).toLocaleDateString("pt-BR")}</p>
        <p>${turma.ultimaNoticia.corpo}</p>
        <p>Autor: ${turma.ultimaNoticia.autor}</p>
        </div>
    ` : `
        <h2>Notícia.</h2>
        <div><p>Não há notícias disponíveis.</p></div>
    `;

    proximoCronogramaEl.innerHTML = turma.proximoCronograma ? `
        <h2>Cronograma</h2>
        <div>
        <h3>${turma.proximoCronograma.titulo}</h3>
        <p>${new Date(turma.proximoCronograma.data).toLocaleDateString("pt-BR")}</p>
        <p>${turma.proximoCronograma.descricao}</p>
        <p>Autor: ${turma.proximoCronograma.autor}</p>
        </div>
    ` : `
        <h2>Cronograma</h2>
        <div><p>Não há cronogramas disponíveis.</p></div>
    `;

}

async function montarTabelaTurma(idTurma) {

    const divDocente = document.querySelector("#docentes");
    const divDiscente = document.querySelector("#discentes");
    const participantes = await fetchJSON("/api/turma/participantes/" + encodeURIComponent(idTurma))

    if (!participantes) return;

    divDocente.innerHTML = "";
    divDiscente.innerHTML = "";

    document.querySelector("#h1-docentes").textContent = `Docentes (${participantes.docentes.length})`;
    document.querySelector("#h1-discentes").textContent = `Discentes (${participantes.discentes.length})`;

    participantes.docentes.forEach(docente => {
        const div = criarCard(docente);
        divDocente.appendChild(div);
    });
    
    participantes.discentes.forEach(discente => {
        const div = criarCard(discente);
        divDiscente.appendChild(div);
    });

}

function criarCard(pessoa) {

    const div = document.createElement("div");
    div.classList.add("pessoa");

    const foto = document.createElement("img");

    const divTexto = document.createElement("div");
    const nome = document.createElement("p");
    nome.classList.add("nome");

    const linhaExtra1 = document.createElement("p");
    const linhaExtra2 = document.createElement("p");
    const email = document.createElement("p");

    if(pessoa.foto === null || pessoa.foto === "") foto.src = fotoPadrao;
    else foto.src = "/images" + pessoa.foto;
    div.appendChild(foto);

    nome.textContent = pessoa.nome;
    divTexto.appendChild(nome);

    if (pessoa.papel === "DOCENTE") [linhaExtra1.textContent, linhaExtra2.textContent] = [pessoa.departamento, pessoa.formacao];
    else [linhaExtra1.textContent, linhaExtra2.textContent] = [pessoa.matricula, pessoa.curso];
    
    divTexto.appendChild(linhaExtra1);
    divTexto.appendChild(linhaExtra2);

    email.textContent = pessoa.email;
    divTexto.appendChild(email);

    div.appendChild(divTexto);

    return div;
}

async function listarNoticias(turmaId) {

    const divNoticias = document.querySelector("#turmas-content .noticias");

    const noticias = await fetchJSON("/api/aluno/acesso/turma/noticias/" + encodeURIComponent(turmaId));

    divNoticias.innerHTML = "";

    noticias.forEach(noticia => {
        const noticiaDiv = document.createElement("div");
        noticiaDiv.classList.add("noticia");
        const manchete = document.createElement("h3");
        manchete.textContent = noticia.manchete;
        const corpo = document.createElement("p");
        corpo.textContent = noticia.corpo;
        const data = document.createElement("p");
        data.textContent = new Date(noticia.data).toLocaleDateString("pt-BR");
        const autor = document.createElement("p");
        autor.textContent = noticia.autor;

        noticiaDiv.appendChild(manchete);
        noticiaDiv.appendChild(data);
        noticiaDiv.appendChild(corpo);
        noticiaDiv.appendChild(autor);
        divNoticias.appendChild(noticiaDiv);

    });


}

async function listarCronograma(turmaId) {

    const divCronogramas = document.querySelector("#turmas-content .cronograma");

    const cronogramas = await fetchJSON("/api/aluno/acesso/turma/cronogramas/" + encodeURIComponent(turmaId));

    divCronogramas.innerHTML = "";
    
    cronogramas.forEach(cronograma => {

        const cronogramaDiv = document.createElement("div");
        cronogramaDiv.classList.add("cronograma-item");

        const titulo = document.createElement("h3");
        titulo.textContent = cronograma.titulo;

        const descricao = document.createElement("p");
        descricao.textContent = cronograma.descricao;

        const tipo = document.createElement("p");
        tipo.classList.add("tipo-cronograma");

        switch (cronograma.tipo) {
            case "REPOSICAO":
                tipo.textContent = "REPOSIÇÃO";
                break;
            case "REVISAO":
                tipo.textContent = "REVISÃO";
                break;
            default:
                tipo.textContent = cronograma.tipo;
        }
    
        const data = document.createElement("p");
        data.textContent = new Date(cronograma.data).toLocaleDateString("pt-BR");

        const autor = document.createElement("b");
        autor.textContent = cronograma.autor;

        cronogramaDiv.appendChild(titulo);
        cronogramaDiv.appendChild(data);
        cronogramaDiv.appendChild(autor);
        cronogramaDiv.appendChild(descricao);
        cronogramaDiv.appendChild(tipo);

        divCronogramas.appendChild(cronogramaDiv);

    });

}

async function montarTabelaFrequencia(turmaId) {
    
    const tabelaFrequencia = document.querySelector("#turmas-content .tabela-frequencia tbody");

    const frequencias = await fetchJSON("/api/aluno/frequencia/" + encodeURIComponent(turmaId));
    tabelaFrequencia.innerHTML = "";
    
    const frequenciaTabelada = [];
    
    frequencias.forEach(registro => {
        if (frequenciaTabelada.find(f => f.data === registro.data) !== undefined) {
            if (!registro.presente) frequenciaTabelada.find(f => f.data === registro.data).faltas++;
        }
        else 
        {
            frequenciaTabelada.push({ data: registro.data, faltas: 0 });
            if (!registro.presente) frequenciaTabelada.find(f => f.data === registro.data).faltas++;
        }
    });

    frequenciaTabelada.forEach(registro => {
        const tr = document.createElement("tr");
        const tdData = document.createElement("td");
        tdData.textContent = new Date(registro.data).toLocaleDateString("pt-BR");
        const tdFaltas = document.createElement("td");
        tdFaltas.textContent = (registro.faltas == 0) ? "Presente" : (registro.faltas + " falta" + (registro.faltas > 1 ? "s" : ""));
        tr.appendChild(tdData);
        tr.appendChild(tdFaltas);
        tabelaFrequencia.appendChild(tr);
    });

}

async function selecionarTurma(turmaId, botao) {
    document.querySelectorAll(".botao-turma").forEach(b => b.classList.remove("ativo"));

    const turma = await fetchJSON("/api/aluno/acesso/turma/info/" + encodeURIComponent(turmaId));

    botao.classList.add("ativo");

    carregarPrincipal(turmaId);
    montarTabelaTurma(turmaId);
    listarNoticias(turmaId);
    listarCronograma(turmaId);
    montarTabelaFrequencia(turmaId);

    // Adiciona classe visivel ao elemento de ações
    const acoesElement = document.querySelector("#turmas-content header .acoes");
    if (acoesElement) {
        acoesElement.classList.add("visivel");
    }

    switchTurmasTab("principal");

}