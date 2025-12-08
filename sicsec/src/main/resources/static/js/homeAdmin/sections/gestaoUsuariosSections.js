const input = (placeholder, type = "text", name = "") => {
    const el = document.createElement("input");
    el.type = type;
    el.placeholder = placeholder;
    if (name) el.name = name;
    return el;
};

function cadastrarNovoUsuario(content) {
    content.innerHTML = "";

    const select = document.createElement("select");

    const opcaoPadrao = new Option("Selecione", "", true, true);
    opcaoPadrao.disabled = true;
    select.add(opcaoPadrao);

    const tipos = ["discente", "docente", "administrador"];
    tipos.forEach(tipo => {
        select.append(new Option(tipo.charAt(0).toUpperCase() + tipo.slice(1), tipo));
    });

    const form = document.createElement("form");
    form.id = "form-novo-usuario";

    const campoNome = input("Nome completo", "text", "nome");
    campoNome.classList.add("escondido");

    const campoEmail = input("Email", "email", "email");
    campoEmail.classList.add("escondido");

    form.append(campoNome, campoEmail);

    const containerExtras = document.createElement("div");
    form.appendChild(containerExtras);

    const btnEnviar = document.createElement("button");
    btnEnviar.type = "submit";
    btnEnviar.textContent = "Cadastrar usuário";
    form.appendChild(btnEnviar);

    select.addEventListener("change", () => {
        campoNome.classList.remove("escondido");
        campoEmail.classList.remove("escondido");
        atualizarCampos();
    });

    function atualizarCampos() {
        containerExtras.innerHTML = "";

        if (select.value === "discente") {
            const matricula = Object.assign(input("Matrícula", "text", "matricula"), {
                className: "campo-extra",
                value: gerarNumMatricula(),
                readOnly: true
            });
            const dataNascimento = Object.assign(input("Data de nascimento", "date", "data_nascimento"), { className: "campo-extra" });

            containerExtras.append(matricula, dataNascimento);
        } else if (select.value === "docente") {
            const area = Object.assign(input("Área de atuação", "text", "area"), { className: "campo-extra" });
            const departamento = Object.assign(input("Departamento", "text", "departamento"), { className: "campo-extra" });

            containerExtras.append(area, departamento);
        } else if (select.value === "administrador") {
            const nivel = Object.assign(input("Nível de acesso", "number", "nivel_acesso"), { className: "campo-extra" });

            containerExtras.append(nivel);
        }
    }

    form.addEventListener("submit", e => {
        e.preventDefault();
        const dados = Object.fromEntries(new FormData(form).entries());
        console.log("Usuário cadastrado:", dados);
    });

    content.append(select, form);
}



function removerUsuario(content) {
    content.innerHTML = "";

    const form = document.createElement("form");
    const select = document.createElement("select");

    const opcaoPadrao = new Option("Selecione", "", true, true);
    opcaoPadrao.disabled = true;
    select.add(opcaoPadrao);

    const tipos = ["discente", "docente", "administrador"];
    tipos.forEach(tipo => {
        select.append(new Option(tipo.charAt(0).toUpperCase() + tipo.slice(1), tipo));
    });

    form.appendChild(select);

    const campoIdent = document.createElement("div");
    form.appendChild(campoIdent);

    let identificador;

    select.addEventListener("change", adicionarRastreador)

    function adicionarRastreador() {

        campoIdent.innerHTML = "";

        let placeholder;

        if(select.value === "discente") {
            placeholder = "Número de matrícula";
        }
        else {
            placeholder = "Número de identificação";
        }

        identificador = input(placeholder, "number", "identificador");

        campoIdent.appendChild(identificador);
    }

    const btnDelete = document.createElement("button");
    btnDelete.type = "submit";
    btnDelete.textContent = "Remover usuário";
    form.appendChild(btnDelete);

    form.addEventListener("submit", e => {
        e.preventDefault();
        console.log(`Usuário ${identificador.value} removido`);
    });

    content.appendChild(form);
}

function initGestao() {
    const btnAddUser = document.querySelector("#btn-criar-usuario");
    const btnRemoveUser = document.querySelector("#btn-remover-usuario");
    const content = document.querySelector("#content");

    btnAddUser.addEventListener("click", () => cadastrarNovoUsuario(content));
    btnRemoveUser.addEventListener("click", () => removerUsuario(content));
}
