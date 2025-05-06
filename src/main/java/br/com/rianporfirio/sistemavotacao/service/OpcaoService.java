package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Opcao;
import br.com.rianporfirio.sistemavotacao.dto.FuncionarioResponse;
import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import br.com.rianporfirio.sistemavotacao.repository.IOpcaoRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcaoService {

    private final IOpcaoRepository opcaoRepository;

    public OpcaoService(IOpcaoRepository opcaoRepository) {
        this.opcaoRepository = opcaoRepository;
    }

    public void create(OpcaoDto dto) {
        opcaoRepository.save(new Opcao(dto));
    }

    public Opcao getOpcao(long opcaoId) {
        return opcaoRepository.findById(opcaoId)
                .orElseThrow(() -> new ValidationException("Opção não encontrada"));
    }

    public List<FuncionarioResponse> getFuncionarios(long opcaoId) {
        var funcionarios = getOpcao(opcaoId)
                .getFuncionarios();
        return funcionarios.stream().map(FuncionarioResponse::new).toList();
    }

    public void delete(long opcaoId) {
        opcaoRepository.deleteById(opcaoId);
    }

    public void deleteCascade(List<Long> opcoes) {
        opcaoRepository.deleteAllById(opcoes);
    }

    public void update(OpcaoDto dto, long opcaoId) {
        var opcao = getOpcao(opcaoId);
        opcao.setNome(dto.nome());
        opcaoRepository.save(opcao);
    }

    public List<Opcao> findAll() {
        return opcaoRepository.findAll();
    }
}
