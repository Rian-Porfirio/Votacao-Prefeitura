package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;

public record EmpresaFormDto(String nome, String cnpj, String descricao) {
    public EmpresaFormDto(Empresa empresa) {
        this(empresa.getNome(), empresa.getCnpj(), empresa.getDescricao());
    }
}
