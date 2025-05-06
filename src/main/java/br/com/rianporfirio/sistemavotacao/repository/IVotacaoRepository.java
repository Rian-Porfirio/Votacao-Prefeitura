package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVotacaoRepository extends JpaRepository<Votacao, Long> {
}
