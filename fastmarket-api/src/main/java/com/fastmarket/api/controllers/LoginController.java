package com.fastmarket.api.controllers;

import com.fastmarket.api.domains.Acesso;
import com.fastmarket.api.domains.Login;
import com.fastmarket.api.dtos.LoginDto;
import com.fastmarket.api.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService LoginService;
    @PostMapping
    public ResponseEntity<Login> createLogin(@RequestBody @Valid LoginDto LoginDto){
        Login newLogin = this.LoginService.createLogin(LoginDto);
        return new ResponseEntity<>(newLogin, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Login>> getAllLogins(){
        return this.LoginService.getAllLogins();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Login> getLoginById(@PathVariable Long id) throws Exception {
        Login Login = LoginService.findLoginById(id);
        return new ResponseEntity<>(Login, HttpStatus.OK);
    }
    @GetMapping("/getByMatricula/{matricula}")
    public ResponseEntity<Login> getLoginByMatricula(@PathVariable int matricula) throws Exception {
        Login Login = LoginService.findLoginByMatricula(matricula);
        return new ResponseEntity<>(Login, HttpStatus.OK);
    }
    @PatchMapping("updateSenha/{id}/senha={senha}")
    public ResponseEntity<Login> updateSenha(@PathVariable Long id, @PathVariable String senha) throws Exception {
        Login newLogin = this.LoginService.updateSenha(senha, id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("updateAcesso/{id}/acesso={acesso}")
    public ResponseEntity<Login> updateSenha(@PathVariable Long id, @PathVariable Acesso acesso) throws Exception {
        Login newLogin = this.LoginService.updateAcesso(acesso, id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("updateAtivo/{id}/{ativo}")
    public ResponseEntity<Login> updateSenha(@PathVariable Long id, @PathVariable boolean ativo) throws Exception {
        Login newLogin = this.LoginService.updateAtivo(ativo, id);
        return ResponseEntity.noContent().build();
    }
}
