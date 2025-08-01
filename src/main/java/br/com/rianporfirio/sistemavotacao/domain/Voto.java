package br.com.rianporfirio.sistemavotacao.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Voto(Funcionario funcionario, Empresa empresa, LocalDateTime data) {
        this.funcionario = funcionario;
        this.empresa = empresa;
        this.data = data;
    }

    @OneToOne
    private Funcionario funcionario;

    @ManyToOne
    private Empresa empresa;

    private LocalDateTime data;
}
