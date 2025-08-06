package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;

public record EmpresaDto (long id,
                         String nome,
                         String cnpj,
                         String descricao,
                         String filePath,
                         boolean isAtivo){
    public EmpresaDto(Empresa empresa) {
        this(
                empresa.getId(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getDescricao(),
                empresa.getFilePath(),
                empresa.isAtivo()
        );
    }
}
