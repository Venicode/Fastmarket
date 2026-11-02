package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ItemPedido")
@Table(name = "TB_ITEMPEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto produto;
    @JoinColumn(name = "id_pedido")
    @ManyToOne
    private Pedido pedido;
    private double valor;
    private int quantidade;
    private double desconto;
    private double troco;
}
