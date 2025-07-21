document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".company-card");
    cards.forEach((card, index) => {
        card.classList.add("fade-in-card");
        card.style.animationDelay = `${index * 100}ms`; // atraso em cascata
    });
});
