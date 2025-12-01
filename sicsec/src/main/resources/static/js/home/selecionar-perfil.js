document.addEventListener("DOMContentLoaded", () => {
    const select = document.querySelector("#perfil-select");
    select.addEventListener("change", () => {
        document.querySelector("#perfil-form").submit();
    });
})
