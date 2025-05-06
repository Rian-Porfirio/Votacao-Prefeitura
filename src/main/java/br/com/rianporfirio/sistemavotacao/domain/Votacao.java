package br.com.rianporfirio.sistemavotacao.domain;

import br.com.rianporfirio.sistemavotacao.dto.VotacaoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Votacao {

    public Votacao(VotacaoDto dto) {
        setNome(dto.nome());
        setDataInicio(dto.dataInicio());
        setDataFim(dto.dataFim());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @OneToMany(mappedBy = "votacao", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcao> opcoes;

}
