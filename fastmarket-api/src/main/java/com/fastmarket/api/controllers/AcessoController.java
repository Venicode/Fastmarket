package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Acesso;
import com.fastmarket.api.dtos.AcessoDto;
import com.fastmarket.api.services.AcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acesso")
public class AcessoController {
    @Autowired
    private AcessoService AcessoService;
    @PostMapping
    public ResponseEntity<Acesso> createAcesso(@RequestBody @Valid AcessoDto AcessoDto){
        Acesso newAcesso = this.AcessoService.createAcesso(AcessoDto);
        return new ResponseEntity<>(newAcesso, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Acesso>> getAllAcessos(){
        return this.AcessoService.getAllAcessos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Acesso> getAcessoById(@PathVariable Long id) throws Exception {
        Acesso Acesso = AcessoService.findAcessoById(id);
        return new ResponseEntity<>(Acesso, HttpStatus.OK);
    }
    @GetMapping("/AcessoDescription/{AcessoDescription}")
    public ResponseEntity<Acesso> getAcessoByAcessoDescription(@PathVariable String AcessoDescription) throws Exception {
        Acesso Acesso = AcessoService.findAcessoByAcessoDescricao(AcessoDescription);
        return new ResponseEntity<>(Acesso, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Acesso> updateAcesso(@RequestBody @Valid AcessoDto AcessoDto, @PathVariable Long id) throws Exception {
        Acesso newAcesso = this.AcessoService.updateAcesso(AcessoDto, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAcesso(@PathVariable Long id){
        this.AcessoService.deleteAcesso(id);
        return ResponseEntity.noContent().build();
    }
}
