package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.StatusPedido;
import com.fastmarket.api.dtos.StatusPedidoDto;
import com.fastmarket.api.services.StatusPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusPedidoController {
    @Autowired
    private StatusPedidoService StatusPedidoService;
    @PostMapping
    public ResponseEntity<StatusPedido> createStatusPedido(@RequestBody @Valid StatusPedidoDto StatusPedidoDto){
        StatusPedido newStatusPedido = this.StatusPedidoService.createStatusPedido(StatusPedidoDto);
        return new ResponseEntity<>(newStatusPedido, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<StatusPedido>> getAllStatusPedido(){
        return this.StatusPedidoService.getAllStatusPedido();
    }
    @GetMapping("/{id}")
    public ResponseEntity<StatusPedido> getStatusPedidoById(@PathVariable Long id) throws Exception {
        StatusPedido StatusPedido = StatusPedidoService.findStatusPedidoById(id);
        return new ResponseEntity<>(StatusPedido, HttpStatus.OK);
    }
    @GetMapping("/StatusPedidoDescription/{StatusPedidoDescription}")
    public ResponseEntity<StatusPedido> getStatusPedidoByStatusPedidoDescription(@PathVariable String StatusPedidoDescription) throws Exception {
        StatusPedido StatusPedido = StatusPedidoService.findStatusPedidoByStatusPedidoDescription(StatusPedidoDescription);
        return new ResponseEntity<>(StatusPedido, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StatusPedido> updateStatusPedido(@RequestBody @Valid StatusPedidoDto StatusPedidoDto, @PathVariable Long id) throws Exception {
        StatusPedido newStatusPedido = this.StatusPedidoService.updateStatusPedido(StatusPedidoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStatusPedido(@PathVariable Long id){
        this.StatusPedidoService.deleteStatusPedido(id);
        return ResponseEntity.noContent().build();
    }
}
