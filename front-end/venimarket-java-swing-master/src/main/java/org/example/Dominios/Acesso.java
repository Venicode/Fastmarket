package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Acesso {
    private Long id;
    private String acesso;

    public String fromMap(Acesso acesso) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(acesso);
    }
    public String toString() {
        return acesso;
    }
}
