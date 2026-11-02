package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private Long id;
    private String cpf;
    private String primeiroNome;
    private String ultimoNome;
    private String telefone;
    private String email;
    private boolean ativo;

    public String fromMap(Cliente cliente) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cliente);
    }
    @Override
    public String toString() {
        return primeiroNome;
    }
}
