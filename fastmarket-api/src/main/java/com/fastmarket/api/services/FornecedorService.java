package com.fastmarket.api.services;

import com.fastmarket.api.repositories.FornecedorRepository;
import com.fastmarket.api.domains.Fornecedor;
import com.fastmarket.api.dtos.FornecedorDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor createFornecedor(FornecedorDto fornecedorDto){
        Fornecedor newFornecedor = new Fornecedor();
        newFornecedor.setCnpj(fornecedorDto.cnpj());
        newFornecedor.setEmail(fornecedorDto.email());
        newFornecedor.setTelefone(fornecedorDto.telefone());
        newFornecedor.setRazaoSocial(fornecedorDto.nomeFantasia());
        newFornecedor.setNomeFantasia(fornecedorDto.razaoSocial());
        newFornecedor.setAtivo(fornecedorDto.ativo());
        this.fornecedorRepository.save(newFornecedor);
        return newFornecedor;
    }
    public Fornecedor updateFornecedor(FornecedorDto fornecedorDto, Long id) throws Exception {
        Fornecedor newFornecedor = findFornecedorById(id);
        newFornecedor.setCnpj(fornecedorDto.cnpj());
        newFornecedor.setEmail(fornecedorDto.email());
        newFornecedor.setTelefone(fornecedorDto.telefone());
        newFornecedor.setRazaoSocial(fornecedorDto.nomeFantasia());
        newFornecedor.setNomeFantasia(fornecedorDto.razaoSocial());
        newFornecedor.setAtivo(fornecedorDto.ativo());

        this.fornecedorRepository.save(newFornecedor);
        return newFornecedor;
    }
    public Fornecedor findFornecedorById(Long id) throws Exception{
        return this.fornecedorRepository.findFornecedorById(id)
                .orElseThrow(()-> new EntityNotFoundException("Fornecedor not found."));

    }

    public ResponseEntity<List<Fornecedor>> getAllFornecedors() {
        List<Fornecedor> fornecedorList = this.fornecedorRepository.findAll();
        return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
    }
    public void disableFornecedor(Long id){
        this.fornecedorRepository.disableFornecedor(id);
    }
}
