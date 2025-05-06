package br.com.rianporfirio.sistemavotacao.domain;

import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Opcao {

    public Opcao(OpcaoDto dto) {
        setNome(dto.nome());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    @OneToMany(mappedBy = "opcao", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios;

    @ManyToOne
    private Votacao votacao;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] foto;

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
