package br.com.rianporfirio.sistemavotacao.repository;

import br.com.rianporfirio.sistemavotacao.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVotoRepository extends JpaRepository<Voto, Long> {
}
