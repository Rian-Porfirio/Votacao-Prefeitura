package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Voto;
import br.com.rianporfirio.sistemavotacao.dto.EmpresaFormDto;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.repository.IVotoRepository;
import br.com.rianporfirio.sistemavotacao.service.validations.EmpresaValidationService;
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
    private final IVotoRepository votoRepository;
    private final UploadImageService uploadImageService;
    private final EmpresaValidationService validation;

    public EmpresaService(IEmpresaRepository empresaRepository, IFuncionarioRepository funcionarioRepository, IVotoRepository votoRepository, UploadImageService uploadImageService, EmpresaValidationService validation) {
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.votoRepository = votoRepository;
        this.uploadImageService = uploadImageService;
        this.validation = validation;
    }

    public void create(EmpresaFormDto dto, MultipartFile file) throws IOException {
        validation.alreadyExistsValidation(dto);

        String nomeEmpresa = dto.nome().trim();
        String filePath = uploadImageService.uploadLogo(file, nomeEmpresa);
        empresaRepository.save(new Empresa(filePath, nomeEmpresa, dto.cnpj(), dto.descricao().trim()));
    }

    public Empresa getEmpresa(long empresaId) {
        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ValidationException("Opção não encontrada"));
    }

    public void delete(long empresaId) throws Exception {
        List<Funcionario> funcionarioList = getEmpresa(empresaId).getFuncionarios();
        List<Voto> votosList = votoRepository.findByEmpresa_Id(empresaId);

        try {
            funcionarioList.forEach(f -> f.setEmpresa(null));
            funcionarioRepository.saveAll(funcionarioList);

            votoRepository.deleteAll(votosList);
            uploadImageService.deleteDirectory(getEmpresa(empresaId).getNome());
            empresaRepository.deleteById(empresaId);

            log.info("deletado com sucesso");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void update(EmpresaFormDto dto, long empresaId, MultipartFile file) throws IOException {
        Empresa empresa = getEmpresa(empresaId);
        validation.updateValidation(empresa, dto);

        String nomeEmpresa = dto.nome().trim();
        empresa.setNome(nomeEmpresa);
        empresa.setCnpj(dto.cnpj());
        empresa.setDescricao(dto.descricao());

        String newPath = uploadImageService.updateFolderName(empresa.getFilePath(), nomeEmpresa);
        empresa.setFilePath(newPath);

        if (!file.isEmpty()) {
            String filePath = uploadImageService.uploadLogo(file, nomeEmpresa);
            empresa.setFilePath(filePath);
        }

        empresaRepository.save(empresa);
    }

    public List<Empresa> getAll() {
        Sort sort = Sort.by("ativo").ascending().and(
                Sort.by("nome").ascending());
        return empresaRepository.findAll(sort);
    }

    public List<Empresa> listByName(String name) {
        return empresaRepository.findAllByNomeContainingIgnoreCase(name);
    }

    public void changeStatus(long empresaId) {
        var empresa = getEmpresa(empresaId);
        empresa.setAtivo(!empresa.isAtivo());
        empresaRepository.save(empresa);
    }
}
