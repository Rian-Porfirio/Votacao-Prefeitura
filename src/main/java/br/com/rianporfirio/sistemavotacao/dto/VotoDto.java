package br.com.rianporfirio.sistemavotacao.dto;

import jakarta.validation.constraints.NotNull;

public record VotoDto(@NotNull Long empresaId){
}
