package com.fastmarket.api.services;

import com.fastmarket.api.domains.CategoriaProduto;
import com.fastmarket.api.domains.Produto;
import com.fastmarket.api.repositories.ProdutoRepository;
import com.fastmarket.api.domains.Fornecedor;
import com.fastmarket.api.dtos.ProdutoDto;
import com.fastmarket.api.dtos.EstoqueDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private FornecedorService fornecedorService;
    @Autowired
    private EstoqueService estoqueService;
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto createProduto(ProdutoDto produtoDto) throws Exception{
        Fornecedor fornecedor = fornecedorService.findFornecedorById(produtoDto.fornecedor().getId());
        CategoriaProduto ProdutoCategory = categoriaProdutoService.findCategoriaProdutoById(produtoDto.categoriaProduto().getId());
        Produto newProduto = new Produto();
        newProduto.setCategoriaProduto(ProdutoCategory);
        newProduto.setMarca(produtoDto.marca());
        newProduto.setDescricao(produtoDto.descricao());
        newProduto.setCodigoBarras(produtoDto.codigoBarras());
        newProduto.setPrecoCusto(produtoDto.precoCusto());
        newProduto.setPrecoVenda(produtoDto.precoVenda());
        newProduto.setQuantidade(produtoDto.quantidade());
        newProduto.setFornecedor(fornecedor);
        newProduto.setAtivo(produtoDto.ativo());

        this.produtoRepository.save(newProduto);

        EstoqueDto newEstoqueDto = new EstoqueDto(newProduto, newProduto.getQuantidade());
        this.estoqueService.createEstoque(newEstoqueDto);
        return newProduto;
    }
    public Produto updateProduto(ProdutoDto produtoDto, Long id) throws Exception{
        Fornecedor fornecedor = fornecedorService.findFornecedorById(produtoDto.fornecedor().getId());
        CategoriaProduto ProdutoCategory = categoriaProdutoService.findCategoriaProdutoById(produtoDto.categoriaProduto().getId());
        Produto newProduto = findProdutoById(id);
        newProduto.setCategoriaProduto(ProdutoCategory);
        newProduto.setMarca(produtoDto.marca());
        newProduto.setDescricao(produtoDto.descricao());
        newProduto.setCodigoBarras(produtoDto.codigoBarras());
        newProduto.setPrecoCusto(produtoDto.precoCusto());
        newProduto.setPrecoVenda(produtoDto.precoVenda());
        newProduto.setQuantidade(produtoDto.quantidade());
        newProduto.setFornecedor(fornecedor);
        newProduto.setAtivo(produtoDto.ativo());

        this.produtoRepository.save(newProduto);

        EstoqueDto newEstoqueDto = new EstoqueDto(newProduto, newProduto.getQuantidade());
        this.estoqueService.createEstoque(newEstoqueDto);
        return newProduto;
    }
    public Produto findProdutoById(Long id) throws Exception{
        return this.produtoRepository.findProdutoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto not found."));

    }
    public Produto findProdutoByIBarCode(Long barcode) throws Exception{
        return this.produtoRepository.findProdutoByCodigoBarras(barcode)
                .orElseThrow(()-> new EntityNotFoundException("Produto not found."));

    }
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtoList = this.produtoRepository.findAll();
        return new ResponseEntity<>(produtoList, HttpStatus.OK);
    }
    public void disableProduto(Long id){
        this.produtoRepository.disableProduto(id);
    }
}
