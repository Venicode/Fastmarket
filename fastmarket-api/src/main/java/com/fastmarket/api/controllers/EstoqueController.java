package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Estoque;
import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.dtos.EstoqueDto;
import com.fastmarket.api.dtos.PedidoDto;
import com.fastmarket.api.services.EstoqueService;
import com.fastmarket.api.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/estoque")
public class EstoqueController {
    @Autowired
    private EstoqueService EstoqueService;

    @PostMapping
    public ResponseEntity<Estoque> cadastrarEstoque(@RequestBody @Valid EstoqueDto EstoqueDto) throws Exception{
        Estoque Estoque = this.EstoqueService.createEstoque(EstoqueDto);
        return new ResponseEntity<>(Estoque, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Estoque>> getAllEstoques(){
        return this.EstoqueService.getAllEstoques();
    }
}
