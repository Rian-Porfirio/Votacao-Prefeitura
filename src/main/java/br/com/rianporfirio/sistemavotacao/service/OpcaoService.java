package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Opcao;
import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IOpcaoRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OpcaoService {

    private final IOpcaoRepository opcaoRepository;
    private final IFuncionarioRepository funcionarioRepository;

    public OpcaoService(IOpcaoRepository opcaoRepository, IFuncionarioRepository funcionarioRepository) {
        this.opcaoRepository = opcaoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void create(OpcaoDto dto) {
        opcaoRepository.save(new Opcao(dto));
    }

    public Opcao getOpcao(long opcaoId) {
        return opcaoRepository.findById(opcaoId)
                .orElseThrow(() -> new ValidationException("Opção não encontrada"));
    }

    public void delete(long opcaoId) {
        try {
            var funcionarioList = listFuncionarios(opcaoId);

            for (var funcionario : funcionarioList) {
                funcionario.setOpcao(null);
            }

            funcionarioRepository.saveAll(funcionarioList);

            opcaoRepository.deleteById(opcaoId);
            log.info("deletado com sucesso");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void update(OpcaoDto dto, long opcaoId) {
        var opcao = getOpcao(opcaoId);
        opcao.setNome(dto.nome());
        opcaoRepository.save(opcao);
    }

    public List<Opcao> getAll() {
        return opcaoRepository.findAll();
    }

    public List<Funcionario> listFuncionarios(long opcaoId) {
        return getOpcao(opcaoId).getFuncionarios();
    }
}
