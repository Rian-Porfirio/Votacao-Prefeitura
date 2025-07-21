const dragContainer = document.getElementById('dragContainer');
const dragArea = document.getElementById('dragArea')
const inputImagem = document.getElementById('imageAdd');
const imagemPreview = document.getElementById('previewImage');

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

function setErrorMessage() {
    const errorMessage = document.createElement('span');
    errorMessage.textContent = "Tipo de arquivo inválido. Envie PNG, JPG ou JPEG .";
    errorMessage.className = 'error-message';

    dragContainer.appendChild(errorMessage);
    dragContainer.classList.add('image-not-valid');
}

function handleFileUpload(event) {
    const file = event.dataTransfer?.files[0] || event.target?.files[0];

    if (!file) {
        return;
    }

    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    inputImagem.files = dataTransfer.files;

    const error = dragContainer.querySelector('.error-message');

    if (error) {
        dragContainer.removeChild(error);
        dragContainer.classList.remove('image-not-valid');
    }

    if (!isFileTypeValid(file)) {
        setErrorMessage();
        return;
    }

    let reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = (e) => {
        imagemPreview.style.removeProperty('object-fit')
        imagemPreview.src = e.target.result;
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

dragArea.addEventListener('drop', handleFileUpload);
inputImagem.addEventListener('change', handleFileUpload);
