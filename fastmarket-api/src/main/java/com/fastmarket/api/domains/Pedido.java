package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Pedido")
@Table(name = "TB_PEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "id_cliente")
    @ManyToOne
    private Cliente cliente;
    @JoinColumn(name = "id_metodoPagamento")
    @ManyToOne
    private MetodoPagamento metodoPagamento;
    private LocalDateTime dataVenda;
    @ManyToOne
    private StatusPedido statusPedido;
    @ManyToOne
    private TipoPedido tipoPedido;
    @ManyToOne
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;
    private String enderecoEntrega;
    private String entregador;
}
