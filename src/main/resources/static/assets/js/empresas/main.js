import './inputValidation.js';
import './uploadFile.js';
import './actions.js';

const campos = [
    {
        input: document.getElementById('nameModalAdd'),
        span: document.getElementById('nomeEmpresa'),
    },
    {
        input: document.getElementById('cnpjModalAdd'),
        span: document.getElementById('cnpj'),
    },
    {
        input: document.getElementById('descricaoModalAdd'),
        span: document.getElementById('descricao'),
    }
];

campos.forEach(campo => {
    campo.originalText = campo.span.textContent;

    campo.input.addEventListener('input', () => {
        const valor = campo.input.value.trim();

        campo.span.textContent = valor === '' ? campo.originalText : valor;
    });
});
