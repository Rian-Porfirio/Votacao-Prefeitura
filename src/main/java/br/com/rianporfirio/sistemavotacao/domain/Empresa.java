package br.com.rianporfirio.sistemavotacao.domain;

import br.com.rianporfirio.sistemavotacao.dto.EmpresaDto;
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
public class Empresa {

    public Empresa(EmpresaDto dto) {
        setNome(dto.nome());
    }

    public Empresa(String nome, String filePath) {
        this.nome = nome;
        this.filePath = filePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filePath;
    private String nome;
    private String cnpj;
    private boolean ativo;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

}
