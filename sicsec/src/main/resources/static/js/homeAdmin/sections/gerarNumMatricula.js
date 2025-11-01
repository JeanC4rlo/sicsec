function gerarNumMatricula() {
    let matricula = "2025";
    return matricula += numeroAleatorio();
}

function numeroAleatorio() {
    const min = 10**5;
    const max = 10**6 - 1;
    return Math.floor(Math.random() * (max - min + 1) + min);
}