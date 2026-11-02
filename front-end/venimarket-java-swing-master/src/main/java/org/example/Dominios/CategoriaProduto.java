package org.example.Dominios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaProduto {
    private Long id;

    private String categoriaProduto;

    public String fromMap(CategoriaProduto categoriaProduto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(categoriaProduto);
    }
    public String toString() {
        return categoriaProduto;
    }
}
