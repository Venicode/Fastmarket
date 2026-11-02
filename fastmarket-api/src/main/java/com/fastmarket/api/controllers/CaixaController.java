package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Caixa;
import com.fastmarket.api.dtos.AbrirCaixaDto;
import com.fastmarket.api.dtos.FecharCaixaDto;
import com.fastmarket.api.services.CaixaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService; 

    @PostMapping("/abrir")
    public ResponseEntity<Caixa> abrir(@RequestBody @Valid AbrirCaixaDto dto) {
        Caixa caixa = caixaService.abrir(dto.loginId(), dto.valorInicial());
        return ResponseEntity.status(HttpStatus.CREATED).body(caixa);
    }

    @PostMapping("/{id}/fechar")
    public ResponseEntity<Caixa> fechar(@PathVariable Long id,
                                        @RequestBody @Valid FecharCaixaDto dto) {
        return ResponseEntity.ok(caixaService.fechar(id, dto.valorContado()));
    }

    @GetMapping("/aberto")
    public ResponseEntity<Caixa> aberto() {
        return caixaService.buscarAberto()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
