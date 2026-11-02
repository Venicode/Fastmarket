package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class TipoPedido {
    private Long id;
    private String tipoPedido;

    public String fromMap(TipoPedido itemPedido) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(itemPedido);
    }
    @Override
    public String toString() {
        return tipoPedido;
    }
}
