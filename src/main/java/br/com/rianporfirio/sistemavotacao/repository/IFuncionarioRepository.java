package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findByMatricula(String matricula);

    List<Funcionario> findBySenhaIsNullOrVinculoIgnoreCase(String vinculo);
    List<Funcionario> findBySenhaIsNullAndVinculoNotIgnoreCase(String vinculo);
    List<Funcionario> findBySenhaIsNotNullAndVinculoNotIgnoreCase(String vinculo);

    Page<Funcionario> findBySenhaIsNotNullAndVinculoNotIgnoreCase(String vinculo, Pageable pageable); //aptos
    Page<Funcionario> findBySenhaIsNullOrVinculoIgnoreCase(String vinculo, Pageable pageable); //inaptos
    Page<Funcionario> findBySenhaIsNullAndVinculoNotIgnoreCase(String vinculo, Pageable pageable); //sem_senha
    Page<Funcionario> findByEmpresaIsNullAndVinculoNotIgnoreCase(String vinculo, Pageable pageable); //nao_votou

    Page<Funcionario> findBySenhaIsNotNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(String vinculo, String nome, Pageable pageable); //aptos
    Page<Funcionario> findBySenhaIsNullOrVinculoIgnoreCaseAndNomeContainingIgnoreCase(String vinculo, String nome, Pageable pageable); //inaptos
    Page<Funcionario> findBySenhaIsNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(String vinculo, String nome, Pageable pageable); //sem_senha
    Page<Funcionario> findByEmpresaIsNullAndVinculoNotIgnoreCaseAndNomeContainingIgnoreCase(String vinculo, String nome, Pageable pageable); //nao_votou
    Page<Funcionario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
