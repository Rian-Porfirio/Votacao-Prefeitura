package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Votacao;
import br.com.rianporfirio.sistemavotacao.dto.VotacaoDto;
import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import br.com.rianporfirio.sistemavotacao.repository.IVotacaoRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotacaoService {

    private final IVotacaoRepository votacaoRepository;

    public VotacaoService(IVotacaoRepository votacaoRepository) {
        this.votacaoRepository = votacaoRepository;
    }

    public Votacao getVotacao(long votacaoId) {
        return votacaoRepository.findById(votacaoId)
                .orElseThrow(() -> new ValidationException("Votação não encontrada"));
    }

    public List<OpcaoDto> getOpcoes(long votacaoId) {
        var opcoesList = getVotacao(votacaoId)
                .getOpcoes();
        return opcoesList.stream().map(OpcaoDto::new).toList();
    }

    public void update(VotacaoDto dto, long votacaoId) {
        var votacao = getVotacao(votacaoId);

        votacao.setNome(dto.nome());
        votacao.setDataInicio(dto.dataInicio());
        votacao.setDataFim(dto.dataFim());
        votacaoRepository.save(votacao);
    }
}
