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
public class Produto {
    private Long id;
    private String descricao;
    private double precoCusto;
    private double precoVenda;
    private Fornecedor fornecedor;
    private int quantidade;
    private CategoriaProduto categoriaProduto;
    private Long codigoBarras;
    private String marca;
    private boolean ativo;

    //Convert to Map
    Map<String,Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("descricao", descricao);
        map.put("precoCusto", precoCusto);
        map.put("precoVenda", precoVenda);
        map.put("id_fornecedor", fornecedor.getId());
        map.put("quantidade", quantidade);
        map.put("id_categoriaProduto", categoriaProduto);
        map.put("codigoBarras", codigoBarras);
        map.put("marca", marca);
        map.put("isAtivo", ativo);
        return map;
    }

    public String fromMap(Produto produto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(produto);
    }
    @Override
    public String toString() {
        return descricao;
    }
}
