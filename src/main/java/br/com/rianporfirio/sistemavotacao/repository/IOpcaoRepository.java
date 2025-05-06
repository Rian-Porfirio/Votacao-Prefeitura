package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Opcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOpcaoRepository extends JpaRepository<Opcao, Long> {
}
