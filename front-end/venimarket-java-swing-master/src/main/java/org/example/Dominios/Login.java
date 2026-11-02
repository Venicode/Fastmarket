package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Login {
    private Long id;
    private int matricula;
    private String nome;
    private String senha;
    private Acesso acesso;
    private boolean ativo;

    public String fromMap(Login login) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(login);
    }
    public String toString() {
        return nome;
    }
}
