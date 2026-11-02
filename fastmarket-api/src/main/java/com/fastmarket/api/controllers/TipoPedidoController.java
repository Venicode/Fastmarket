package com.fastmarket.api.controllers;

import com.fastmarket.api.dtos.TipoPedidoDto;
import com.fastmarket.api.services.TipoPedidoService;
import com.fastmarket.api.domains.TipoPedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoPedido")
public class TipoPedidoController {
    @Autowired
    private TipoPedidoService TipoPedidoService;
    @PostMapping
    public ResponseEntity<TipoPedido> createTipoPedido(@RequestBody @Valid TipoPedidoDto TipoPedidoDto){
        TipoPedido newTipoPedido = this.TipoPedidoService.createTipoPedido(TipoPedidoDto);
        return new ResponseEntity<>(newTipoPedido, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TipoPedido>> getAllTipoPedidos(){
        return this.TipoPedidoService.getAllTipoPedidos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoPedido> getTipoPedidoById(@PathVariable Long id) throws Exception {
        TipoPedido TipoPedido = TipoPedidoService.findTipoPedidoById(id);
        return new ResponseEntity<>(TipoPedido, HttpStatus.OK);
    }
    @GetMapping("/TipoPedidoDescription/{TipoPedidoDescription}")
    public ResponseEntity<TipoPedido> getTipoPedidoByTipoPedidoDescription(@PathVariable String TipoPedidoDescription) throws Exception {
        TipoPedido TipoPedido = TipoPedidoService.findTipoPedidoByTipoPedidoDescription(TipoPedidoDescription);
        return new ResponseEntity<>(TipoPedido, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TipoPedido> updateTipoPedido(@RequestBody @Valid TipoPedidoDto TipoPedidoDto, @PathVariable Long id) throws Exception {
        TipoPedido newTipoPedido = this.TipoPedidoService.updateTipoPedido(TipoPedidoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTipoPedido(@PathVariable Long id){
        this.TipoPedidoService.deleteTipoPedido(id);
        return ResponseEntity.noContent().build();
    }
}
