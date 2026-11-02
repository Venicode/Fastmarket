package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    private Long id;
    private Cliente cliente;
    private MetodoPagamento metodoPagamento;
    private LocalDateTime dataVenda;
    private StatusPedido statusPedido;
    private TipoPedido tipoPedido;
    private Caixa caixa;
    private String enderecoEntrega;
    private String entregador;

    public String fromMap(Pedido pedido) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(pedido);
    }
}
