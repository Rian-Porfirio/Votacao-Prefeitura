package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Voto;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IVotoRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class VoteGenerationService {

    private final IFuncionarioRepository repository;
    private final IEmpresaRepository empresaRepository;
    private final IVotoRepository votoRepository;

    public VoteGenerationService(IFuncionarioRepository repository, IEmpresaRepository empresaRepository, IVotoRepository votoRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.votoRepository = votoRepository;
    }

    public void generateVotes() throws Exception {
        List<Funcionario> funcionarios = repository.findByEmpresaIsNullAndVinculoNotIgnoreCase("estagiário");

        if (funcionarios.isEmpty()) {
            throw new ValidationException("Todos os funcionários já possuem voto registrado.");
        }

        Empresa empresa = empresaRepository.findMostVoted();
        if (empresa == null) {
            throw new ValidationException("Não há empresas cadastradas no sistema");
        }

        try {
            for (var funcionario : funcionarios) {
                log.info("iniciando votação de {}", funcionario.getNome());

                funcionario.votar(empresa);
                repository.save(funcionario);

                saveVote(funcionario, empresa);
                log.info("o funcionario {} teve seu voto inserido em {}", funcionario.getNome(), empresa.getNome());
            }
        } catch (Exception ex) {
            log.error("Ocorreu um erro ao inserir voto: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    private void saveVote(Funcionario funcionario, Empresa empresa) throws Exception {
        try {
            Voto voto = votoRepository.findByFuncionario(funcionario);

            if (voto != null) {
                votoRepository.delete(voto);
            }

            votoRepository.save(new Voto(funcionario, empresa, LocalDateTime.now()));
        } catch (Exception ex) {
            throw new Exception("Ocorreu um erro ao criar registro de voto");
        }
    }
}
