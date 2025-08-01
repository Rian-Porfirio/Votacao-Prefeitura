package br.com.rianporfirio.sistemavotacao.domain;

import br.com.rianporfirio.sistemavotacao.dto.EmpresaDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Empresa {

    public Empresa(EmpresaDto dto) {
        setNome(dto.nome());
    }

    public Empresa(String filePath, String nome, String cnpj, String descricao) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.filePath = filePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filePath;
    private String nome;
    private String cnpj;
    private String descricao;
    private boolean ativo;

    @ToString.Exclude
    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

}
