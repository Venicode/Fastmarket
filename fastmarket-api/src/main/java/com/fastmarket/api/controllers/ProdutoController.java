package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Produto;
import com.fastmarket.api.dtos.ProdutoDto;
import com.fastmarket.api.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService ProdutoService;

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody @Valid ProdutoDto ProdutoDto) throws Exception{
        Produto newProduto = this.ProdutoService.createProduto(ProdutoDto);
        return new ResponseEntity<>(newProduto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos(){
        return this.ProdutoService.getAllProdutos();
    }
    @GetMapping("/{barcode}")
    public ResponseEntity<Produto> getProdutoByBarCode(@PathVariable Long barcode) throws Exception {
        Produto Produto = this.ProdutoService.findProdutoByIBarCode(barcode);
        return new ResponseEntity<>(Produto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) throws Exception {
        Produto Produto = this.ProdutoService.findProdutoById(id);
        return new ResponseEntity<>(Produto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduto(@RequestBody @Valid ProdutoDto ProdutoDto, @PathVariable Long id) throws Exception{
        Produto newProduto = this.ProdutoService.updateProduto(ProdutoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableProduto(@PathVariable Long id){
        this.ProdutoService.disableProduto(id);
        return ResponseEntity.noContent().build();
    }
}
