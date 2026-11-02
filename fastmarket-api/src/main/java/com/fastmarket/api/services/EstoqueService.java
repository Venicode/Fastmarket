package com.fastmarket.api.services;

import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.repositories.EstoqueRepository;
import com.fastmarket.api.domains.Produto;
import com.fastmarket.api.domains.Estoque;
import com.fastmarket.api.dtos.EstoqueDto;
import com.fastmarket.api.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    public Estoque createEstoque(EstoqueDto estoqueDto){
        Estoque newEstoque = new Estoque();
        newEstoque.setProduto(estoqueDto.produto());
        newEstoque.setQuantidade(estoqueDto.produto().getQuantidade());

        this.estoqueRepository.save(newEstoque);
        return newEstoque;
    }
    public void updateEstoque(int quantity, Produto produto) throws Exception {
        produto.setQuantidade(quantity);
        this.estoqueRepository.updateQuantidade(quantity, produto);
    }
    public ResponseEntity<List<Estoque>> getAllEstoques() {
        List<Estoque> estoqueList = this.estoqueRepository.findAll();
        return new ResponseEntity<>(estoqueList, HttpStatus.OK);
    }
}
