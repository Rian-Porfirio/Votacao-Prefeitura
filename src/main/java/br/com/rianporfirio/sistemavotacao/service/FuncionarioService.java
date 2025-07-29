package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Voto;
import br.com.rianporfirio.sistemavotacao.dto.FuncionarioInfoDto;
import br.com.rianporfirio.sistemavotacao.error.exceptions.VoteAlreadyRegisteredException;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.repository.IVotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;
    private final IEmpresaRepository empresaRepository;
    private final IVotoRepository votoRepository;
    private final String vinculo = "ESTAGIÁRIO";
    private final int pageSize = 60;

    public FuncionarioService(IFuncionarioRepository funcionarioRepository, IEmpresaRepository empresaRepository, IVotoRepository votoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.empresaRepository = empresaRepository;
        this.votoRepository = votoRepository;
    }

    public void inserirVoto(long empresaId, String matricula) {
        var empresa = empresaRepository.findById(empresaId).get();
        var funcionario = funcionarioRepository.findByMatricula(matricula);

        if(funcionario.getEmpresa() != null) {
            throw new VoteAlreadyRegisteredException("Usuário já possui voto registrado");
        }

        funcionario.votar(empresa);
        funcionarioRepository.save(funcionario);
        votoRepository.save(new Voto(funcionario, empresa, LocalDateTime.now()));
    }

    public List<Funcionario> getAll() {
        Sort sort = Sort.by("nome");
        return funcionarioRepository.findAll(sort);
    }

    public FuncionarioInfoDto loadInformation() {
        int semSenha = loadAllSemSenha().size();
        int inaptos = loadAllInaptos().size();
        int cadastrados = getAll().size();
        int aptos = loadAllAptos().size();

        return new FuncionarioInfoDto(cadastrados, aptos, inaptos, semSenha);
    }

    public List<Funcionario> loadAllInaptos() {
        return funcionarioRepository.findBySenhaIsNullOrVinculoIgnoreCase(vinculo);
    }

    public List<Funcionario> loadAllSemSenha() {
        return funcionarioRepository.findBySenhaIsNullAndVinculoNotIgnoreCase(vinculo);
    }

    public List<Funcionario> loadAllAptos() {
        return funcionarioRepository.findBySenhaIsNotNullAndVinculoNotIgnoreCase(vinculo);
    }

    public Page<Funcionario> loadPages(String search, String status, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("nome").ascending());

        if (search != null && !search.isBlank()) {
            return searchByPage(search, status, pageable);
        }

        return loadPageByStatus(status, pageable);
    }

    private Page<Funcionario> searchByPage(String search, String status, Pageable pageable) {
        if (status == null) {
            return funcionarioRepository.findByNomeContainingIgnoreCase(search, pageable);
        }

        return switch (status) {
            case "aptos" -> funcionarioRepository.findBySenhaIsNotNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(vinculo, search, pageable);
            case "inaptos" -> funcionarioRepository.findBySenhaIsNullOrVinculoIgnoreCaseAndNomeContainingIgnoreCase(vinculo, search, pageable);
            case "sem_senha" -> funcionarioRepository.findBySenhaIsNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(vinculo, search, pageable);
            case "nao_votou" -> funcionarioRepository.findByEmpresaIsNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(vinculo, search, pageable);
            default -> funcionarioRepository.findByNomeContainingIgnoreCase(search, pageable);
        };
    }

    private Page<Funcionario> loadPageByStatus(String status, Pageable pageable) {
        if (status == null) {
            return funcionarioRepository.findAll(pageable);
        }

        return switch (status) {
            case "aptos" -> funcionarioRepository.findBySenhaIsNotNullAndVinculoNotIgnoreCase(vinculo, pageable);
            case "inaptos" -> funcionarioRepository.findBySenhaIsNullOrVinculoIgnoreCase(vinculo, pageable);
            case "sem_senha" -> funcionarioRepository.findBySenhaIsNullAndVinculoNotIgnoreCase(vinculo, pageable);
            case "nao_votou" -> funcionarioRepository.findByEmpresaIsNullAndVinculoNotIgnoreCase(vinculo, pageable);
            default -> funcionarioRepository.findAll(pageable);
        };
    }
}
