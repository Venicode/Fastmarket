package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ItemPedido {
    private Long id;
    private Produto produto;
    private Pedido pedido;
    private double valor;
    private int quantidade;
    private double desconto;
    private double troco;

    public String fromMap(ItemPedido itemPedido) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(itemPedido);
    }
}
