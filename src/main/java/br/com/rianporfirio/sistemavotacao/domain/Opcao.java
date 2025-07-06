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

    public Opcao(String nome, String filePath) {
        this.nome = nome;
        this.filePath = filePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filePath;
    private String nome;
    private String cnpj;

    @OneToMany(mappedBy = "opcao", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

}
