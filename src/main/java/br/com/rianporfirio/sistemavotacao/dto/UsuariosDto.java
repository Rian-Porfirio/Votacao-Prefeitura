package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;

public record UsuariosDto (String matricula,
                           String nome,
                           String cargo,
                           String vinculo,
                           String localDeTrabalho,
                           String lotacao){
    public UsuariosDto(Funcionario funcionario) {
        this(
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getVinculo(),
                funcionario.getLocalDeTrabalho(),
                funcionario.getLotacao()
        );
    }
}
