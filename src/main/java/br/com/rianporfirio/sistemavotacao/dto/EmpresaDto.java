package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;

public record EmpresaDto(String nome) {
    public EmpresaDto(Empresa empresa) {
        this(empresa.getNome());
    }
}
