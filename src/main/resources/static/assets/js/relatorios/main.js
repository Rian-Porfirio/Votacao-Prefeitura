const generateVotesForm = document.getElementById('genetareVotesForm')
const generateVoteModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('generateVotesModal'));

const errorModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalErrorToggle'));
const errorModalMessage = document.getElementById("errorModalMessage");

const successModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalSuccessToggle'));
const successMessage = document.getElementById('successMessage');

const generateVotes = document.getElementById('generateVotes');

generateVotesForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    const response = await fetch("/gerarVotos", {
        body: formData,
        method: "POST",
    });

    if (!response.ok) {
        generateVoteModal.hide();
        errorModalMessage.textContent = await response.text();
        errorModal.show();
        finishProcessing();
        return;
    }

    generateVoteModal.hide();
    finishProcessing();
    success();
});

function success() {
    successMessage.textContent = "Votos registrados com sucesso. Agora todos os servidores possuem votos registrados."
    successModal.show();
}

const processingButton = document.getElementById('processingVote');

generateVotes.addEventListener('click', processing);

function processing() {
    processingButton.classList.remove('modal-votos__processing');
    generateVotes.classList.add('modal-votos__processing');
}

function finishProcessing() {
    processingButton.classList.add('modal-votos__processing');
    generateVotes.classList.remove('modal-votos__processing');
}