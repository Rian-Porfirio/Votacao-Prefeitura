package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;

public record VotosEmpresaDto(String empresa, int votos) {
    public VotosEmpresaDto(Empresa empresa) {
        this(empresa.getNome(), empresa.getFuncionarios().size());
    }
}
