package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Estoque")
@Table(name = "TB_ESTOQUE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto produto;
    private int quantidade;
}
