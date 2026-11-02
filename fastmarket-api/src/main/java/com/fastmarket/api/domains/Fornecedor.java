package com.fastmarket.api.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity(name = "Fornecedor")
@Table(name = "TB_FORNECEDOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cnpj;
    @Column(unique = true)
    private String razaoSocial;
    private String nomeFantasia;
    @Column(unique = true)
    private String telefone;
    @Column(unique = true)
    @Email
    private String email;
    private boolean isAtivo = true;
}
