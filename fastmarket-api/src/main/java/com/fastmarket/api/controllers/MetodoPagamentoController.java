package com.fastmarket.api.controllers;

import com.fastmarket.api.services.MetodoPagamentoService;
import com.fastmarket.api.domains.MetodoPagamento;
import com.fastmarket.api.dtos.MetodoPagamentoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPagamento")
public class MetodoPagamentoController {
    @Autowired
    private MetodoPagamentoService MetodoPagamentoService;
    @PostMapping
    public ResponseEntity<MetodoPagamento> createMetodoPagamento(@RequestBody @Valid MetodoPagamentoDto MetodoPagamentoDto){
        MetodoPagamento newMetodoPagamento = this.MetodoPagamentoService.createMetodoPagamento(MetodoPagamentoDto);
        return new ResponseEntity<>(newMetodoPagamento, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MetodoPagamento>> getAllMetodoPagamentos(){
        return this.MetodoPagamentoService.getAllMetodoPagamentos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagamento> getMetodoPagamentoById(@PathVariable Long id) throws Exception {
       MetodoPagamento MetodoPagamento = MetodoPagamentoService.findMetodoPagamentoById(id);
       return new ResponseEntity<>(MetodoPagamento, HttpStatus.OK);
    }
    @GetMapping("/descricaoMetodoPagamento/{descricaoMetodoPagamento}")
    public ResponseEntity<MetodoPagamento> getMetodoPagamentoByDescriptionMetodoPagamento(@PathVariable String descriptionMetodoPagamento) throws Exception {
        MetodoPagamento MetodoPagamento = MetodoPagamentoService.findMetodoPagamentoByDescriptionMetodoPagamento(descriptionMetodoPagamento);
        return new ResponseEntity<>(MetodoPagamento, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamento> updateMetodoPagamento(@RequestBody @Valid MetodoPagamentoDto MetodoPagamentoDto, @PathVariable Long id) throws Exception {
        MetodoPagamento newMetodoPagamento = this.MetodoPagamentoService.updateMetodoPagamento(MetodoPagamentoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMetodoPagamento(@PathVariable Long id){
        this.MetodoPagamentoService.deleteMetodoPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
