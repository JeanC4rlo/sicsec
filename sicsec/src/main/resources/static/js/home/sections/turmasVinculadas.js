const fotoPadrao = "../imagens/foto-padrao.png";
let listaTurmas;
let materiaAtual;
let localAtual;
let horarioAtual;

const TURMAS = [
    {
        nome: "Português",
        local: "Sala 101",
        horario: "Segunda e Quarta - 08:00 às 09:40",
        docentes: [
            {
                papel: "DOCENTE",
                foto: fotoPadrao,
                nome: "Marina Albuquerque",
                departamento: "Linguagens e Comunicação",
                formacao: "Mestrado em Letras",
                email: "marina.albuquerque@escola.edu.br"
            },
            {
                papel: "DOCENTE",
                foto: fotoPadrao,
                nome: "Carlos Nogueira",
                departamento: "Linguagens e Comunicação",
                formacao: "Doutorado em Literatura Brasileira",
                email: "carlos.nogueira@escola.edu.br"
            }
        ],
        discentes: [
            {
                papel: "DISCENTE",
                foto: fotoPadrao,
                nome: "Laura Mendes",
                matricula: "20230012",
                curso: "Ensino Médio Integrado",
                email: "laura.mendes@aluno.escola.edu.br"
            },
            {
                papel: "DISCENTE",
                foto: fotoPadrao,
                nome: "Rafael Silva",
                matricula: "20230045",
                curso: "Ensino Médio Integrado",
                email: "rafael.silva@aluno.escola.edu.br"
            },
            {
                papel: "DISCENTE",
                foto: fotoPadrao,
                nome: "Jéssica Oliveira",
                matricula: "20230078",
                curso: "Ensino Médio Integrado",
                email: "jessica.oliveira@aluno.escola.edu.br"
            }
        ]
    },
    {
        nome: "Matemática",
        local: "Laboratório 2",
        horario: "Terça e Quinta - 10:00 às 11:40",
        docentes: [
            {
                papel: "DOCENTE",
                foto: fotoPadrao,
                nome: "Pedro Andrade",
                departamento: "Ciências Exatas",
                formacao: "Mestrado em Matemática Aplicada",
                email: "pedro.andrade@escola.edu.br"
            }
        ],
        discentes: [
            {
                papel: "DISCENTE",
                foto: fotoPadrao,
                nome: "Lucas Ferreira",
                matricula: "20230101",
                curso: "Ensino Médio Integrado",
                email: "lucas.ferreira@aluno.escola.edu.br"
            }
        ]
    }
];

function montarTabelaTurma(nomeTurma) {
    const divDocente = document.querySelector("#docentes");
    const divDiscente = document.querySelector("#discentes");
    const turma = TURMAS.find(t => t.nome === nomeTurma);
    if (!turma) return;
    divDocente.innerHTML = "";
    divDiscente.innerHTML = "";
    document.querySelector("#h1-docentes").textContent = `Docentes (${turma.docentes.length})`;
    document.querySelector("#h1-discentes").textContent = `Discentes (${turma.discentes.length})`;
    turma.docentes.forEach(docente => {
        const div = criarCard(docente);
        divDocente.appendChild(div);
    });
    turma.discentes.forEach(discente => {
        const div = criarCard(discente);
        divDiscente.appendChild(div);
    });
}

function criarCard(pessoa) {
    const div = document.createElement("div");
    const foto = document.createElement("img");
    const nome = document.createElement("p");
    nome.classList.add("nome");
    const linhaExtra1 = document.createElement("p");
    const linhaExtra2 = document.createElement("p");
    const email = document.createElement("p");
    div.classList.add("pessoa");
    foto.src = pessoa.foto;
    div.appendChild(foto);
    nome.textContent = pessoa.nome;
    div.appendChild(nome);
    if (pessoa.papel === "DOCENTE") {
        linhaExtra1.textContent = pessoa.departamento;
        linhaExtra2.textContent = pessoa.formacao;
    } else {
        linhaExtra1.textContent = pessoa.matricula;
        linhaExtra2.textContent = pessoa.curso;
    }
    div.appendChild(linhaExtra1);
    div.appendChild(linhaExtra2);
    email.textContent = pessoa.email;
    div.appendChild(email);
    return div;
}

window.addEventListener("load", () => {
    
});

function selecionarTurma(turma, botao) {
    document.querySelectorAll(".botao-turma").forEach(b => b.classList.remove("ativo"));
    botao.classList.add("ativo");
    materiaAtual.textContent = turma.nome;
    localAtual.textContent = turma.local;
    horarioAtual.textContent = turma.horario;
    montarTabelaTurma(turma.nome);
    
    // Adiciona classe visivel ao elemento de ações
    const acoesElement = document.querySelector("#turmas header .acoes");
    if (acoesElement) {
        acoesElement.classList.add("visivel");
    }
    
    sessionStorage.setItem("turmaSelecionada", turma.nome);
}

function initTurmas() {
    listaTurmas = document.querySelector("#turmas .lista-turmas");
    materiaAtual = document.querySelector(".value.materia");
    localAtual = document.querySelector(".value.local");
    horarioAtual = document.querySelector(".value.horario");

    TURMAS.forEach(turma => {
        const botao = document.createElement("button");
        botao.textContent = turma.nome;
        botao.classList.add("botao-turma");
        botao.addEventListener("click", () => selecionarTurma(turma, botao));
        listaTurmas.appendChild(botao);
    });

    const turmaSalva = sessionStorage.getItem("turmaSelecionada");
    if (turmaSalva) {
        const botao = [...document.querySelectorAll(".botao-turma")].find(b => b.textContent === turmaSalva);
        const turma = TURMAS.find(t => t.nome === turmaSalva);
        if (botao && turma) {
            selecionarTurma(turma, botao);
        }
    } else {
        // Remove a classe visivel se não houver turma selecionada
        const acoesElement = document.querySelector("#turmas header .acoes");
        if (acoesElement) {
            acoesElement.classList.remove("visivel");
        }
    }
}