package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.dtos.PedidoDto;
import com.fastmarket.api.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> createSell(@RequestBody @Valid PedidoDto PedidoDto) throws Exception{
        Pedido Pedido = this.pedidoService.createPedido(PedidoDto);
        return new ResponseEntity<>(Pedido, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos(){
        return this.pedidoService.getAllPedidos();
    }
    @GetMapping("/delivery/andamento")
    public ResponseEntity<List<Pedido>> deliveryEmAndamento() {
        return ResponseEntity.ok(pedidoService.listarPorTipoEStatus("Delivery", "Em andamento"));
    }

    @PutMapping("/{id}/status/{statusId}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @PathVariable Long statusId) {
        pedidoService.atualizarStatus(id, statusId);
        return ResponseEntity.noContent().build();
    }
}
