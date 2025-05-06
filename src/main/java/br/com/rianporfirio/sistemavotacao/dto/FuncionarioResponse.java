package br.com.rianporfirio.sistemavotacao.dto;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;

public record FuncionarioResponse(String matricula,
                                  String nome,
                                  String vinculo,
                                  String lotacao,
                                  String localDeTrabalho,
                                  String cargo) {
    public FuncionarioResponse(Funcionario funcionario) {
        this(funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getVinculo(),
                funcionario.getLotacao(),
                funcionario.getLocalDeTrabalho(),
                funcionario.getCargo());
    }
}
