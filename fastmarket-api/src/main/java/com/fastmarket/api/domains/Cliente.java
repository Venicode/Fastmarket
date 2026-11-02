package com.fastmarket.api.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "Cliente")
@Table(name = "TB_CLIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cpf;
    private String primeiroNome;
    private String ultimoNome;
    @Column(unique = true)
    private String telefone;
    @Column(unique = true)
    @Email
    private String email;
    private boolean ativo = true;
}
