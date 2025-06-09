package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findByMatricula(String matricula);
}
