package com.fastmarket.api.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Login")
@Table(name = "TB_LOGIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private int matricula;
    private String nome;
    private String senha;
    @JoinColumn(name = "id_acesso")
    @ManyToOne
    private Acesso acesso;
    private boolean ativo = true;
}
