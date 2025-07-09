package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findAllByNomeContainingIgnoreCase(String name);
}
