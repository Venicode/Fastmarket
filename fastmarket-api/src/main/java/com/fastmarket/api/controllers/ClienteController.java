package com.fastmarket.api.controllers;

import com.fastmarket.api.services.ClienteService;
import com.fastmarket.api.domains.Cliente;
import com.fastmarket.api.dtos.ClienteDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService ClienteService;

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody @Valid ClienteDto ClienteDto) throws Exception{
        Cliente newCliente = this.ClienteService.createCliente(ClienteDto);
        return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes(){
        return this.ClienteService.getAllClientes();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@RequestBody @Valid ClienteDto ClienteDto, @PathVariable Long id) throws Exception{
        Cliente newCliente = this.ClienteService.updateCliente(ClienteDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableCliente(@PathVariable Long id){
        this.ClienteService.disableCliente(id);
        return ResponseEntity.noContent().build();
    }
}
