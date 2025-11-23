document.addEventListener("DOMContentLoaded", () => {
    const select = document.querySelector("#perfilSelect");
    select.addEventListener("change", () => {
        document.querySelector("#perfilForm").submit();
    });
})
