package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.ItemPedido;
import com.fastmarket.api.dtos.ItemPedidoDto;
import com.fastmarket.api.repositories.ItemPedidoRepository;
import com.fastmarket.api.services.ItemPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/itemPedido")
public class ItemPedidoController {
    @Autowired
    private ItemPedidoService itemPedidoService;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @PostMapping
    public ResponseEntity<ItemPedido> createSell(@RequestBody @Valid ItemPedidoDto ItemPedidoDto) throws Exception{
        ItemPedido ItemPedido = this.itemPedidoService.createItemPedido(ItemPedidoDto);
        return new ResponseEntity<>(ItemPedido, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ItemPedido>> getAllItemPedidos(){
        return this.itemPedidoService.getAllItemPedidos();
    }
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedido>> porPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(itemPedidoRepository.findByPedidoId(pedidoId));
    }
}
