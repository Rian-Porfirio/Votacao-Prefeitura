package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Opcao;

public record OpcaoDto(String nome) {
    public OpcaoDto(Opcao opcao) {
        this(opcao.getNome());
    }
}
