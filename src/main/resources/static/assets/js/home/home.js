document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".company-card");
    cards.forEach((card, index) => {
        card.classList.add("fade-in-card");
        card.style.animationDelay = `${index * 100}ms`;
    });
});

const voteButtonList = document.querySelectorAll('.company-card__vote-button');
const confirmModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalInformativeToggle'));
const successModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalSuccessToggle'));

const errorModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalErrorToggle'));
const errorMessage = document.getElementById('errorModalMessage');

voteButtonList.forEach(btn => {
    btn.addEventListener('click', () => {
        const input = document.getElementById('confirmInput');
        input.value = btn.getAttribute('empresa');

        const card = btn.closest('.company-card');
        const nomeSpan = card.querySelector('.company-card__title span');

        const nomeEmpresaModal = document.getElementById('nomeEmpresaModal');
        nomeEmpresaModal.textContent = nomeSpan.textContent;

        confirmModal.show();
    });
});

const confirmForm = document.getElementById('confirm');

confirmForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    try {
        const res = await fetch("/votar", {
            body: formData,
            method: 'POST'
        });

        if (!res.ok) {
            errorMessage.textContent = await res.text();
            confirmModal.hide();
            errorModal.show();
            return;
        }

        confirmModal.hide();
        successModal.show();

    } catch (error) {
        errorMessage.textContent = "Ocorreu um erro inesperado. Tente novamente.";
        confirmModal.hide();
        errorModal.show();
    }
});
