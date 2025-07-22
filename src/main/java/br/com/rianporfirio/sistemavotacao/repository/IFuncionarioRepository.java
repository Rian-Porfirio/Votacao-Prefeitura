package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findByMatricula(String matricula);

    List<Funcionario> findBySenhaIsNull();
    List<Funcionario> findBySenhaIsNullAndVinculoNotIgnoreCase(String vinculo);
    List<Funcionario> findBySenhaIsNotNullAndVinculoNotIgnoreCase(String vinculo);

    Page<Funcionario> findBySenhaIsNull(Pageable pageable);
    Page<Funcionario> findBySenhaIsNullAndVinculoNotIgnoreCase(String vinculo, Pageable pageable);
    Page<Funcionario> findBySenhaIsNotNullAndVinculoNotIgnoreCase(String vinculo, Pageable pageable);

    @Query("SELECT f FROM Funcionario f WHERE f.empresa IS NULL")
    Page<Funcionario> findNoVote(Pageable pageable);
}
