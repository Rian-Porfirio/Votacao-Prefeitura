const deleteInput = document.getElementById('deleteInput');
const editInput = document.getElementById('editInput');

document.querySelectorAll(".delete-button").forEach(btn => {
    btn.addEventListener('click', () => {
        deleteInput.value = getEmpresa(btn);
    });
});

function getEmpresa(btn) {
    return btn.parentElement.getAttribute('empresa');
}