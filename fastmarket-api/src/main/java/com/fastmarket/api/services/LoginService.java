package com.fastmarket.api.services;

import com.fastmarket.api.domains.Acesso;
import com.fastmarket.api.domains.Login;
import com.fastmarket.api.dtos.LoginDto;
import com.fastmarket.api.repositories.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginRepository LoginRepository;

    public Login createLogin(LoginDto LoginDto){
        Login newLogin = new Login();
        newLogin.setAcesso(LoginDto.acesso());
        newLogin.setNome(LoginDto.nome());
        newLogin.setMatricula(LoginDto.matricula());
        newLogin.setSenha(LoginDto.senha());
        newLogin.setAtivo(LoginDto.ativo());
        this.LoginRepository.save(newLogin);
        return newLogin;
    }
    public Login updateSenha(String senha, Long id) throws Exception {
        Login newLogin = findLoginById(id);
        newLogin.setSenha(senha);
        this.LoginRepository.save(newLogin);
        return newLogin;
    }
    public Login updateAcesso(Acesso acesso, Long id) throws Exception {
        Login newLogin = findLoginById(id);
        newLogin.setAcesso(acesso);
        this.LoginRepository.save(newLogin);
        return newLogin;
    }
    public Login updateAtivo(boolean ativo, Long id) throws Exception {
        Login newLogin = findLoginById(id);
        newLogin.setAtivo(ativo);
        this.LoginRepository.save(newLogin);
        return newLogin;
    }
    public Login findLoginById(Long id) throws Exception {
        return this.LoginRepository.findLoginById(id)
                .orElseThrow(()-> new EntityNotFoundException("Login não encontrado."));
    }

    public Login findLoginByMatricula(int matricula) throws Exception {
        return this.LoginRepository.findLoginByMatricula(matricula)
                .orElseThrow(()-> new EntityNotFoundException("Login não encontrado."));
    }

    public ResponseEntity<List<Login>> getAllLogins() {
        List<Login> LoginList = this.LoginRepository.findAll();
        return  new ResponseEntity<>(LoginList, HttpStatus.OK);
    }

}
