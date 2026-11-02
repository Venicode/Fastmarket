package com.fastmarket.api.services;

import com.fastmarket.api.repositories.CategoriaProdutoRepository;
import com.fastmarket.api.domains.CategoriaProduto;
import com.fastmarket.api.dtos.CategoriaProdutoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProdutoService {
    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProduto createCategoriaProduto(CategoriaProdutoDto categoriaProdutoDto){
        CategoriaProduto CategoriaProduto = new CategoriaProduto();
        CategoriaProduto.setCategoriaProduto(categoriaProdutoDto.categoriaProduto());
        this.categoriaProdutoRepository.save(CategoriaProduto);
        return CategoriaProduto;
    }
    public CategoriaProduto findCategoriaProdutoById(Long id){
        return this.categoriaProdutoRepository.findCategoriaProdutoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product category not found."));
    }
    public ResponseEntity<List<CategoriaProduto>> getAllProductCategories(){
        List<CategoriaProduto> CategoriaProdutoList =  this.categoriaProdutoRepository.findAll();
        return new ResponseEntity<>(CategoriaProdutoList, HttpStatus.OK);
    }
    public CategoriaProduto updateCategoriaProduto(CategoriaProdutoDto categoriaProdutoDto, Long id){
        CategoriaProduto CategoriaProduto = findCategoriaProdutoById(id);
        CategoriaProduto.setCategoriaProduto(categoriaProdutoDto.categoriaProduto());
        this.categoriaProdutoRepository.save(CategoriaProduto);
        return CategoriaProduto;
    }
    public void deleteCategoriaProduto(Long id){
        this.categoriaProdutoRepository.deleteCategoriaProduto(id);
    }
}
