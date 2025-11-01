document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const cpf = form.cpf.value;
        const senha = form.senha.value;

        const [cpfTratado, senhaTratada] = handleData(cpf, senha);
        validate(cpfTratado, senhaTratada);
    });
});

function validate(cpf, senha) {
    fetch("https://jeanc4rlo.github.io/sicsec-telas/data/perfis.json")
        .then((response) => response.json())
        .then((json) => {
            let perfil = json.find(profile => profile.cpf === cpf)

            if (!perfil) {
                console.log("CPF não encontrado");
                return;
            }

            if (perfil.senha === senha) {
                console.log("Logado com sucesso");
                if(perfil.admin) {
                    window.location.href = `html/homeAdmin.html?cpf=${encodeURIComponent(cpf)}`;
                }
                else {
                    window.location.href = `html/home.html?cpf=${encodeURIComponent(cpf)}`;
                }
            }
            else {
                console.log("Senha incorreta");
            }
        })
}

function handleData(cpf, senha) {
    cpf = cpf.replace(/\D/g, "");
    return [cpf, senha];
}