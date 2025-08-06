package br.com.rianporfirio.sistemavotacao.service.validations;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.dto.EmpresaFormDto;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class EmpresaValidationService {

    private final IEmpresaRepository repository;

    public EmpresaValidationService(IEmpresaRepository repository) {
        this.repository = repository;
    }

    public void alreadyExistsValidation(EmpresaFormDto dto, Long empresaId) {
        String nomeEmpresa = dto.nome().trim();

        Empresa nomeExists = repository.findByNome(nomeEmpresa);
        if (nomeExists != null && notEquals(nomeExists, empresaId)) {
            throw new ValidationException("Este nome já foi registrado");
        }

        Empresa cnpjExists = repository.findByCnpj(dto.cnpj());
        if (cnpjExists != null && notEquals(cnpjExists, empresaId)) {
            throw new ValidationException("Este CNPJ já foi registrado");
        }
    }

    public void alreadyExistsValidation(EmpresaFormDto dto) {
        String nomeEmpresa = dto.nome().trim();

        Empresa nomeExists = repository.findByNome(nomeEmpresa);
        if (nomeExists != null) {
            throw new ValidationException("Este nome já foi registrado");
        }

        Empresa cnpjExists = repository.findByCnpj(dto.cnpj());
        if (cnpjExists != null) {
            throw new ValidationException("Este CNPJ já foi registrado");
        }
    }

    public void updateValidation(Empresa empresa, EmpresaFormDto dto) {
        String name = empresa.getNome();
        String cnpj = empresa.getCnpj();

        boolean isNameEquals = name.equalsIgnoreCase(dto.nome());
        boolean isCnpjEquals = cnpj.equalsIgnoreCase(dto.cnpj());

        if (!isNameEquals || !isCnpjEquals) {
            alreadyExistsValidation(dto, empresa.getId());
        }
    }

    private boolean notEquals(Empresa empresa, Long id) {
        if (id == null) {
            return false;
        }

        return empresa.getId() != id;
    }
}
