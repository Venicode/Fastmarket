package com.fastmarket.api.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Produto")
@Table(name = "TB_PRODUTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String descricao;
    @NotNull
    private double precoCusto;
    @NotNull
    private double precoVenda;
    @JoinColumn(name = "id_fornecedor")
    @ManyToOne
    private Fornecedor fornecedor;
    private int quantidade;
    @JoinColumn(name = "id_categoriaProduto")
    @ManyToOne
    private CategoriaProduto categoriaProduto;
    @Column(unique = true)
    private Long codigoBarras;
    private String marca;
    private boolean isAtivo = true;
}
