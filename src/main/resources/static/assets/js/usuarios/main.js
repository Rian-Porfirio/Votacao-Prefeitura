
document.querySelectorAll('.passwordGenButton').forEach(btn => {
    btn.addEventListener('click', () => {
        const input = document.getElementById('matriculaInput')
        input.value = btn.getAttribute('matricula');
    })
})

document.getElementById('formPasswordGen').addEventListener('submit', (e) => {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);

    fetch('/generate/password', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) throw new Error('Erro na requisição');
            return response.text();
        })
        .then(generatedPassword => {
            document.getElementById('senhaGerada').textContent = generatedPassword;
        })
        .catch(error => console.error('Erro:', error));

});

document.getElementById('modalPasswordGen').addEventListener('hidden.bs.modal', () => {
    document.activeElement.blur()
});