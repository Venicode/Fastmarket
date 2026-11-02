package com.fastmarket.api.services;

import com.fastmarket.api.domains.Acesso;
import com.fastmarket.api.dtos.AcessoDto;
import com.fastmarket.api.repositories.AcessoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessoService {
    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso createAcesso(AcessoDto AcessoDto){
        Acesso newAcesso = new Acesso();
        newAcesso.setAcesso(AcessoDto.acesso());
        this.acessoRepository.save(newAcesso);
        return newAcesso;
    }
    public Acesso updateAcesso(AcessoDto AcessoDto, Long id) throws Exception {
        Acesso newAcesso = findAcessoById(id);
        newAcesso.setAcesso(AcessoDto.acesso());
        this.acessoRepository.save(newAcesso);
        return newAcesso;
    }
    public Acesso findAcessoById(Long id) throws Exception {
        return this.acessoRepository.findAcessoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Acesso não encontrado."));
    }

    public Acesso findAcessoByAcessoDescricao(String AcessoDescricao) throws Exception {
        return this.acessoRepository.findAcessoByAcesso(AcessoDescricao)
                .orElseThrow(()-> new EntityNotFoundException("Acesso não encontrado."));
    }

    public ResponseEntity<List<Acesso>> getAllAcessos() {
        List<Acesso> AcessoList = this.acessoRepository.findAll();
        return  new ResponseEntity<>(AcessoList, HttpStatus.OK);
    }
    public void deleteAcesso(Long id){
        this.acessoRepository.deleteAcesso(id);
    }
}
