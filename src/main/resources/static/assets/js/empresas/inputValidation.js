const cnpjInput = document.getElementById('cnpjModalAdd');

cnpjInput.addEventListener('input', () => {
    let value = cnpjInput.value.replace(/\D/g, '');
    cnpjInput.value = value.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5');
});