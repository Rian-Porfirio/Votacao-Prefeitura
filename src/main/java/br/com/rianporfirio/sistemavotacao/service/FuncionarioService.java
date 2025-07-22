package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.dto.FuncionarioInfoDto;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;
    private final IEmpresaRepository empresaRepository;

    public FuncionarioService(IFuncionarioRepository funcionarioRepository, IEmpresaRepository empresaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.empresaRepository = empresaRepository;
    }

    public void inserirVoto(long empresaId, String matricula) {
        var opcao = empresaRepository.findById(empresaId).get();
        var funcionario = funcionarioRepository.findByMatricula(matricula);
        funcionario.votar(opcao);
        funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> getAll() {
        Sort sort = Sort.by("nome");
        return funcionarioRepository.findAll(sort);
    }

    public List<Funcionario> listByName(List<Funcionario> listaFiltrada, String search) {
        return listaFiltrada.stream()
                .filter(f -> f.getNome().contains(search.toUpperCase()))
                .toList();
    }

    public FuncionarioInfoDto loadInformation() {
        int semSenha = loadAllSemSenha().size();
        int inaptos = loadAllInaptos().size();
        int cadastrados = getAll().size();
        int aptos = loadAllAptos().size();

        return new FuncionarioInfoDto(cadastrados, aptos, inaptos, semSenha);
    }

    public List<Funcionario> loadAllInaptos() {
        return funcionarioRepository.findBySenhaIsNull();
    }

    public List<Funcionario> loadAllSemSenha() {
        return funcionarioRepository.findBySenhaIsNullAndVinculoNotIgnoreCase("ESTAGIÁRIO");
    }

    public List<Funcionario> loadAllAptos() {
        return funcionarioRepository.findBySenhaIsNotNullAndVinculoNotIgnoreCase("ESTAGIÁRIO");
    }

    public Page<Funcionario> loadFilteredPage(String status, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());

        Page<Funcionario> basePage;

        if (status == null) {
            basePage = funcionarioRepository.findAll(pageable);
            return loadSearchFilter(search, basePage, pageable);
        }

        status = status.trim().toLowerCase();

        basePage = switch (status) {
            case "aptos" -> funcionarioRepository.findBySenhaIsNotNullAndVinculoNotIgnoreCase("ESTAGIÁRIO", pageable);
            case "inaptos" -> funcionarioRepository.findBySenhaIsNull(pageable);
            case "sem_senha" -> funcionarioRepository.findBySenhaIsNullAndVinculoNotIgnoreCase("ESTAGIÁRIO", pageable);
            case "nao_votou" -> funcionarioRepository.findNoVote(pageable);
            default -> funcionarioRepository.findAll(pageable);
        };

        return loadSearchFilter(search, basePage, pageable);
    }


    private Page<Funcionario> loadSearchFilter(String search, Page<Funcionario> basePage, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            List<Funcionario> filteredList = basePage.getContent().stream()
                    .filter(f -> f.getNome().contains(search.toUpperCase()))
                    .toList();

            return new PageImpl<>(filteredList, pageable, filteredList.size());
        }

        return basePage;
    }

}
