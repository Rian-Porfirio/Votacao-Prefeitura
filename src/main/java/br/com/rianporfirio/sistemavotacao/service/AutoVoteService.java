package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AutoVoteService {

    private final IFuncionarioRepository repository;
    private final IEmpresaRepository empresaRepository;

    public AutoVoteService(IFuncionarioRepository repository, IEmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    public void autoVote() {
        List<Funcionario> funcionarios = repository.findByEmpresaIsNullAndVinculoNotIgnoreCase("estagiário");

        if (funcionarios.isEmpty()) {
            log.info("Não há funcionários sem voto");
            return;
        }

        try {
            Empresa empresa = empresaRepository.findLessVoted();
            for (var funcionario : funcionarios) {
                log.info("iniciando votação de {}", funcionario.getNome());
                funcionario.votar(empresa);
                repository.save(funcionario);
                log.info("o funcionario {} teve seu voto inserido em {}", funcionario.getNome(), empresa.getNome());
            }
        } catch (Exception ex) {
            log.error("Ocorreu um erro ao inserir voto: {}", ex.getMessage());
            throw ex;
        }
    }
}
