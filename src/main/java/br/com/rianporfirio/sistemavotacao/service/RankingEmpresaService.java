package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.dto.RankingVotosDto;
import br.com.rianporfirio.sistemavotacao.dto.VotosEmpresaDto;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingEmpresaService {

    private final IEmpresaRepository empresaRepository;

    public RankingEmpresaService(IEmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public RankingVotosDto getRanking() {
        List<Empresa> empresas = empresaRepository.findAll();
        List<VotosEmpresaDto> dtoList =  empresas.stream()
                .map(VotosEmpresaDto::new)
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, Integer> rankingOrdenado = ordenarRanking(dtoList);
        return new RankingVotosDto(rankingOrdenado);
    }

    private Map<String, Integer> ordenarRanking(List<VotosEmpresaDto> dtoList) {
        dtoList.sort(Comparator.comparing(VotosEmpresaDto::votos).reversed());

        Map<String, Integer> ranking = new LinkedHashMap<>();
        for (VotosEmpresaDto dto : dtoList) {
            ranking.put(dto.empresa(), dto.votos());
        }

        return ranking;
    }
}
