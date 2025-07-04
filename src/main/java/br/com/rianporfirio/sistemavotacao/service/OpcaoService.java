package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Opcao;
import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IOpcaoRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OpcaoService {

    private final IOpcaoRepository opcaoRepository;
    private final IFuncionarioRepository funcionarioRepository;
    private final UploadImageService uploadImageService;

    public OpcaoService(IOpcaoRepository opcaoRepository, IFuncionarioRepository funcionarioRepository, UploadImageService uploadImageService) {
        this.opcaoRepository = opcaoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.uploadImageService = uploadImageService;
    }

    public void create(OpcaoDto dto, MultipartFile file) throws IOException {
        String filePath = uploadImageService.uploadLogo(file, dto.nome());
        opcaoRepository.save(new Opcao(dto.nome(), filePath));
    }

    public Opcao getOpcao(long opcaoId) {
        return opcaoRepository.findById(opcaoId)
                .orElseThrow(() -> new ValidationException("Opção não encontrada"));
    }

    public void delete(long opcaoId) throws IOException{
        var funcionarioList = listFuncionarios(opcaoId);
        for (var funcionario : funcionarioList) {
            funcionario.setOpcao(null);
        }

        funcionarioRepository.saveAll(funcionarioList);
        uploadImageService.deleteDirectory(getOpcao(opcaoId).getNome());
        opcaoRepository.deleteById(opcaoId);

        log.info("deletado com sucesso");
    }

    public void update(OpcaoDto dto, long opcaoId, MultipartFile file) throws IOException {
        String filePath = uploadImageService.uploadLogo(file, dto.nome());

        var opcao = getOpcao(opcaoId);
        opcao.setNome(dto.nome());
        opcao.setFilePath(filePath);

        opcaoRepository.save(opcao);
    }

    public List<Opcao> getAll() {
        return opcaoRepository.findAll();
    }

    public List<Funcionario> listFuncionarios(long opcaoId) {
        return getOpcao(opcaoId).getFuncionarios();
    }
}
