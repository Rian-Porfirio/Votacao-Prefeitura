package br.com.rianporfirio.sistemavotacao.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "activity_log")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private LocalDateTime date;

    @Embedded
    private HttpDetails httpDetails;
}
