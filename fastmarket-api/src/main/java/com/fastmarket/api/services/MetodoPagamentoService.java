package com.fastmarket.api.services;

import com.fastmarket.api.domains.MetodoPagamento;
import com.fastmarket.api.repositories.MetodoPagamentoRepository;
import com.fastmarket.api.dtos.MetodoPagamentoDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagamentoService {
    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    public MetodoPagamento createMetodoPagamento(MetodoPagamentoDto metodoPagamentoDto){
        MetodoPagamento newMetodoPagamento = new MetodoPagamento();
        newMetodoPagamento.setMetodoPagamento(metodoPagamentoDto.metodoPagamento());
        this.metodoPagamentoRepository.save(newMetodoPagamento);
        return newMetodoPagamento;
    }
    public MetodoPagamento updateMetodoPagamento(MetodoPagamentoDto metodoPagamentoDto, Long id) throws Exception {
        MetodoPagamento newMetodoPagamento = findMetodoPagamentoById(id);
        newMetodoPagamento.setMetodoPagamento(metodoPagamentoDto.metodoPagamento());
        this.metodoPagamentoRepository.save(newMetodoPagamento);
        return newMetodoPagamento;
    }
    public MetodoPagamento findMetodoPagamentoById(Long id) throws Exception {
        return this.metodoPagamentoRepository.findMetodoPagamentoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Method Payment not found."));
    }

    public MetodoPagamento findMetodoPagamentoByDescriptionMetodoPagamento(String descriptionMetodoPagamento) throws Exception {
        return this.metodoPagamentoRepository.findMetodoPagamentoByMetodoPagamento(descriptionMetodoPagamento)
                .orElseThrow(()-> new EntityNotFoundException("Method Payment not found."));
    }

    public ResponseEntity<List<MetodoPagamento>> getAllMetodoPagamentos() {
        List<MetodoPagamento> metodoPagamentoList = this.metodoPagamentoRepository.findAll();
        return  new ResponseEntity<>(metodoPagamentoList, HttpStatus.OK);
    }
    public void deleteMetodoPagamento(Long id){
        this.metodoPagamentoRepository.deleteMetodoPagamento(id);
    }
}
