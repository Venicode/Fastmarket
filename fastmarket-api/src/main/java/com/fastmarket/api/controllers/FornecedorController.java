package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Fornecedor;
import com.fastmarket.api.dtos.FornecedorDto;
import com.fastmarket.api.services.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorService FornecedorService;

    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody @Valid FornecedorDto FornecedorDto) throws Exception{
        Fornecedor newFornecedor = this.FornecedorService.createFornecedor(FornecedorDto);
        return new ResponseEntity<>(newFornecedor, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAllFornecedors(){
        return this.FornecedorService.getAllFornecedors();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFornecedor(@RequestBody @Valid FornecedorDto FornecedorDto, @PathVariable Long id) throws Exception{
        Fornecedor newFornecedor = this.FornecedorService.updateFornecedor(FornecedorDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableFornecedor(@PathVariable Long id){
        this.FornecedorService.disableFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
