package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MetodoPagamento {

    private Long id;
    private String metodoPagamento;

    public String fromMap(MetodoPagamento metodoPagamento) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(metodoPagamento);
    }
    Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("metodoPagamento", metodoPagamento);
        return map;
    }
}
