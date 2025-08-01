package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.dto.EmpresaDto;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class EmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final IFuncionarioRepository funcionarioRepository;
    private final UploadImageService uploadImageService;

    public EmpresaService(IEmpresaRepository empresaRepository, IFuncionarioRepository funcionarioRepository, UploadImageService uploadImageService) {
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.uploadImageService = uploadImageService;
    }

    public void create(EmpresaDto dto, MultipartFile file) throws IOException {
        String nomeEmpresa = dto.nome().trim();
        Empresa empresa = empresaRepository.findByNomeIgnoreCaseOrCnpj(nomeEmpresa, dto.cnpj());
        if (empresa != null) {
            validateInputs(empresa, nomeEmpresa);
        }

        String filePath = uploadImageService.uploadLogo(file, nomeEmpresa);
        empresaRepository.save(new Empresa(filePath, nomeEmpresa, dto.cnpj(), dto.descricao().trim()));
    }

    public Empresa getEmpresa(long empresaId) {
        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ValidationException("Opção não encontrada"));
    }

    public void delete(long empresaId) throws IOException{
        var funcionarioList = listFuncionarios(empresaId);
        for (var funcionario : funcionarioList) {
            funcionario.setEmpresa(null);
        }

        funcionarioRepository.saveAll(funcionarioList);
        uploadImageService.deleteDirectory(getEmpresa(empresaId).getNome());
        empresaRepository.deleteById(empresaId);

        log.info("deletado com sucesso");
    }

    public void update(EmpresaDto dto, long empresaId, MultipartFile file) throws IOException {
        var empresa = getEmpresa(empresaId);

        empresa.setNome(dto.nome());
        empresa.setCnpj(dto.cnpj());
        empresa.setDescricao(dto.descricao());

        if (!file.isEmpty()) {
            String filePath = uploadImageService.uploadLogo(file, dto.nome());
            empresa.setFilePath(filePath);
        }

        empresaRepository.save(empresa);
    }

    public List<Empresa> getAll() {
        Sort sort = Sort.by("ativo").ascending().and(
                Sort.by("nome").ascending());
        return empresaRepository.findAll(sort);
    }

    public List<Funcionario> listFuncionarios(long empresaId) {
        return getEmpresa(empresaId).getFuncionarios();
    }

    public List<Empresa> listByName(String name) {
        return empresaRepository.findAllByNomeContainingIgnoreCase(name);
    }

    public void changeStatus(long empresaId) {
        var empresa = getEmpresa(empresaId);
        empresa.setAtivo(!empresa.isAtivo());
        empresaRepository.save(empresa);
    }

    private void validateInputs(Empresa empresa, String nome) {
        if (empresa.getNome().equals(nome)) {
            throw new ValidationException("Este nome já foi registrado");
        } else {
            throw new ValidationException("Este CNPJ já foi registrado");
        }
    }
}
