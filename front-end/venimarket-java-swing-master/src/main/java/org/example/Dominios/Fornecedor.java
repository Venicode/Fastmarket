package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Fornecedor {
    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String telefone;
    private String email;
    private boolean ativo;
    @Override
    public String toString() {
        return nomeFantasia;
    }
    public String fromMap(Fornecedor fornecedor) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(fornecedor);
    }

}
