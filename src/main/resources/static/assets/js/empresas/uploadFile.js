const dragContainer = document.getElementById('dragContainer');
const dragArea = document.getElementById('dragArea')
const inputImagem = document.getElementById('imageAdd');
const imagemPreview = document.getElementById('previewImage');
const defaultImage = "/assets/images/no-image-background.png";
const maxFileSize = 2 * 1024 * 1024 // 2MB

function preventDefault(event) {
    event.preventDefault();
    event.stopPropagation();
}

function draggingOverFeedback() {
    dragArea.classList.add('drag-over');
}

function leavingFeedback() {
    dragArea.classList.remove('drag-over');
}

function isFileTypeValid(file) {
    const allowedMimeTypes = ["image/png", "image/jpeg", "image/jpg"];
    return allowedMimeTypes.includes(file.type);
}

export function setErrorMessage(error) {
    const errorMessage = document.createElement('p');
    errorMessage.textContent = error;
    errorMessage.className = 'error-message';

    dragContainer.appendChild(errorMessage);
    dragContainer.classList.add('input-error');
}

function handleFileUpload(event) {
    removeErrors();
    const file = event.dataTransfer?.files[0] || event.target?.files[0];

    if (!file) {
        setImagePreview();
        return;
    }

    if (file.size > maxFileSize) {
        setErrorMessage("Arquivo excede o limite de 2 MB");
        return;
    }

    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    inputImagem.files = dataTransfer.files;

    if (!isFileTypeValid(file)) {
        setErrorMessage("Tipo de Arquivo Inválido. Envie PNG, JPG ou JPEG .");
        return;
    }

    let reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = (e) => {
        setImagePreview(e);
    }
}

const dragEvents = ['dragover', 'dragenter', 'dragleave', 'drop'];

dragEvents.map(event => {
    dragArea.addEventListener(event, preventDefault);

    if (event === 'dragleave' || event === 'drop') {
        dragArea.addEventListener(event, leavingFeedback);
    } else {
        dragArea.addEventListener(event, draggingOverFeedback);
    }
})

function setImagePreview(e) {
    if (!e) {
        imagemPreview.src = defaultImage;
        imagemPreview.style.setProperty('object-fit', 'contain');
        return;
    }

    imagemPreview.src = e.target.result;
    imagemPreview.style.setProperty('object-fit', 'cover');
}

function removeErrors() {
    const error = dragContainer.querySelector('.error-message');

    if (error) {
        error.remove();
        dragContainer.classList.remove('input-error');
    }
}

dragArea.addEventListener('drop', handleFileUpload);
inputImagem.addEventListener('change', handleFileUpload);
