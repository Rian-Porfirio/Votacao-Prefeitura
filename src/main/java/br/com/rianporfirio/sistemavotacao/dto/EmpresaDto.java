package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;

public record EmpresaDto(String nome, String cnpj, String descricao) {
    public EmpresaDto(Empresa empresa) {
        this(empresa.getNome(), empresa.getCnpj(), empresa.getDescricao());
    }
}
