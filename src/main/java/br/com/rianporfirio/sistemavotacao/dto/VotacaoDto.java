package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Votacao;

import java.time.LocalDateTime;

public record VotacaoDto(String nome,
         LocalDateTime dataInicio,
         LocalDateTime dataFim) {
    public VotacaoDto(Votacao votacao) {
        this(votacao.getNome(), votacao.getDataInicio(), votacao.getDataFim());
    }
}
