package br.com.rianporfirio.sistemavotacao.dto;

import java.util.Map;

public record RankingVotosDto(Map<String, Integer> empresa) {
}
