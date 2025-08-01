const createForm = document.getElementById('formNovaEmpresa');

const nameInputContainer = document.getElementById('nameInputContainer');
const nameInput = document.getElementById('nameModalAdd');

const cnpjInputContainer = document.getElementById(`cnpjInputContainer`);
const cnpjInput = document.getElementById('cnpjModalAdd');

const dragContainer = document.getElementById('dragContainer');

const modalCreate = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalAddOption'))
const errorModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modalErrorToggle'));

const cancelarBtn = document.getElementById('cancelarBtn');

cnpjInput.addEventListener('input', () => {
    let value = cnpjInput.value.replace(/\D/g, '');
    cnpjInput.value = value.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5');
});

createForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    try {
        const res = await fetch("/create", {
            body: formData,
            method: "POST"
        });

        if (!res.ok) {
            removeErrors();

            let message = await res.text();
            const error = createError(message);

            if (message.includes("Arquivo")) {
                setImageInputError(error);
                return;
            }

            setInputError(error);
            return;
        }

        createForm.reset();
        removeErrors();
        successFeedback();
    } catch (e) {
        const error = createError("Verifique Sua Conexão com Internet ou Contate um Desenvolvedor")
        setModalError(error);
    }
})

function setInputError(error) {
    let errorMessage = error.textContent;

    if (errorMessage.includes("nome")) {
        nameInput.classList.add('is-invalid');
        nameInputContainer.appendChild(error);
    }

    if (errorMessage.includes("CNPJ")) {
        cnpjInput.classList.add('is-invalid');
        cnpjInputContainer.appendChild(error);
    }
}

function setImageInputError(error) {
    dragContainer.appendChild(error);
    dragContainer.classList.add('input-error');
}

function removeErrors() {
    const inputErrors = createForm.querySelector('.is-invalid');
    const imageError = createForm.querySelector('.input-error');
    const errorMessages = createForm.querySelectorAll('.error-message');

    if (inputErrors) {
        nameInput.classList.remove('is-invalid');
        cnpjInput.classList.remove('is-invalid');
    }

    if (imageError) {
        dragContainer.classList.remove('input-error');
    }

    if (errorMessages) {
        errorMessages.forEach(error => error.remove());
    }
}

function createError(message) {
    const error = document.createElement("p");
    error.textContent = message;
    error.className = 'error-message';
    return error;
}

function setModalError(error) {
    const errorModalMessage = document.getElementById("errorModalMessage");
    errorModalMessage.textContent = error.textContent;
    modalCreate.hide();
    errorModal.show();
}

function successFeedback() {
    const btnConfirm = document.getElementById('confirmCreate');
    const confirmText = document.getElementById('confirmText');
    const loading = document.getElementById('loading');
    const successText = document.getElementById('successText');

    btnConfirm.classList.add('filled');
    btnConfirm.disabled = true;

    fadeOut(confirmText);
    setTimeout(() => {
        fadeIn(loading);
    }, 100);

    setTimeout(async () => {
        fadeOut(loading);
        setTimeout(() => fadeIn(successText), 100);
    }, 1500);

    setTimeout(() => {
        fadeOut(successText);
        setTimeout( () => {
            fadeIn(confirmText);
            btnConfirm.classList.remove('filled');
            btnConfirm.disabled = false;
        }, 500);
    }, 3000);
}

function fadeIn(el) {
    el.classList.add('visible');
}

function fadeOut(el) {
    el.classList.remove('visible');
}

cancelarBtn.addEventListener('click', () => {
    window.location.reload();
})
