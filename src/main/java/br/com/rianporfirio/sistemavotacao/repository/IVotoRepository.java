package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVotoRepository extends JpaRepository<Voto, Long> {
    List<Voto> findByEmpresa_Id(long empresaId);
    Voto findByFuncionario(Funcionario funcionario);
}
