async function validateBiblioteca(matriculaTipo, matricula, senha) {

    let response = await fetch("../data/biblioteca.json");

    let json = await response.json();

    let perfil = json.find(profile => profile.matricula === matricula);

    let cpf = new URL(window.location.href).searchParams.get("cpf");
    if (!perfil)
        window.location.href = `home.html?cpf=${encodeURIComponent(cpf)}&erro=${encodeURIComponent(true)}`;
    if (perfil.senha !== senha || perfil.matriculaTipo !== matriculaTipo)
        window.location.href = `home.html?cpf=${encodeURIComponent(cpf)}&erro=${encodeURIComponent(true)}`;

    window.location.href = `home.html?cpf=${encodeURIComponent(cpf)}&matricula=${encodeURIComponent(perfil.matricula)}`;
    return true;

}

function initLoginBiblioteca() {
    const form = document.querySelector("#biblioteca #login form");
    console.log(form);

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        
        const matriculaTipo = form.matriculaTipo.value;
        const matricula = form.matricula.value;
        const senha = form.senha.value;

        form.cpf = document.createElement("form");
        form.cpf.value = new URL(window.location.href).searchParams.get("cpf");

        validateBiblioteca(matriculaTipo, matricula, senha);

    });
}