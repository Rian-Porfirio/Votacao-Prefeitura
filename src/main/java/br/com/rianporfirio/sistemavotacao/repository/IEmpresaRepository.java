package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findAllByNomeContainingIgnoreCase(String name);

    Empresa findByNomeIgnoreCaseOrCnpj(String nome, String cnpj);

    @Query("SELECT e FROM Empresa e LEFT JOIN e.funcionarios f GROUP BY e ORDER BY COUNT(f) ASC LIMIT 1")
    Empresa findLessVoted();
}
