
document.querySelectorAll('.passwordGenButton').forEach(btn => {
    btn.addEventListener('click', () => {
        const input = document.getElementById('matriculaInput')
        input.value = btn.getAttribute('matricula');
    })
})

document.getElementById('formPasswordGen').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);

    const outputSenha = document.getElementById('senhaGerada');

    const response = await fetch('/generate/password', {
        method: 'POST',
        body: formData
    });

    const responseText = await response.text();

    if (!response.ok) {
        outputSenha.textContent = responseText;
        return;
    }

    outputSenha.textContent = responseText;

});

document.getElementById('modalPasswordGen').addEventListener('hidden.bs.modal', () => {
    document.activeElement.blur()
});