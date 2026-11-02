package com.fastmarket.api.controllers;

import com.fastmarket.api.services.CategoriaProdutoService;
import com.fastmarket.api.domains.CategoriaProduto;
import com.fastmarket.api.dtos.CategoriaProdutoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoriaProduto")
public class CategoriaProdutoController {
    @Autowired
    private CategoriaProdutoService CategoriaProdutoService;
    @PostMapping
    public ResponseEntity<CategoriaProduto> createCategoriaProduto(@RequestBody @Valid CategoriaProdutoDto CategoriaProdutoDto){
        CategoriaProduto CategoriaProduto = this.CategoriaProdutoService.createCategoriaProduto(CategoriaProdutoDto);
        return new ResponseEntity<>(CategoriaProduto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CategoriaProduto>> getAllProductCategories(){
        return this.CategoriaProdutoService.getAllProductCategories();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> updateCategoriaProduto(@RequestBody @Valid CategoriaProdutoDto CategoriaProdutoDto, @PathVariable Long id) throws Exception {
        CategoriaProduto CategoriaProduto = this.CategoriaProdutoService.updateCategoriaProduto(CategoriaProdutoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoriaProduto(@PathVariable Long id){
        this.CategoriaProdutoService.deleteCategoriaProduto(id);
        return ResponseEntity.noContent().build();
    }
}
