package com.fastmarket.api.services;

import com.fastmarket.api.dtos.TipoPedidoDto;
import com.fastmarket.api.repositories.TipoPedidoRepository;
import com.fastmarket.api.domains.TipoPedido;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPedidoService {
    @Autowired
    private TipoPedidoRepository tipoPedidoRepository;

    public TipoPedido createTipoPedido(TipoPedidoDto tipoPedidoDto){
        TipoPedido newTipoPedido = new TipoPedido();
        newTipoPedido.setTipoPedido(tipoPedidoDto.tipoPedido());
        this.tipoPedidoRepository.save(newTipoPedido);
        return newTipoPedido;
    }
    public TipoPedido updateTipoPedido(TipoPedidoDto tipoPedidoDto, Long id) throws Exception {
        TipoPedido newTipoPedido = findTipoPedidoById(id);
        newTipoPedido.setTipoPedido(tipoPedidoDto.tipoPedido());
        this.tipoPedidoRepository.save(newTipoPedido);
        return newTipoPedido;
    }
    public TipoPedido findTipoPedidoById(Long id) throws Exception {
        return this.tipoPedidoRepository.findTipoPedidoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order Type not found."));
    }

    public TipoPedido findTipoPedidoByTipoPedidoDescription(String tipoPedido) throws Exception {
        return this.tipoPedidoRepository.findTipoPedidoByTipoPedido(tipoPedido)
                .orElseThrow(()-> new EntityNotFoundException("Order Type not found."));
    }

    public ResponseEntity<List<TipoPedido>> getAllTipoPedidos() {
        List<TipoPedido> tipoPedidoList = this.tipoPedidoRepository.findAll();
        return  new ResponseEntity<>(tipoPedidoList, HttpStatus.OK);
    }
    public void deleteTipoPedido(Long id){
        this.tipoPedidoRepository.deleteTipoPedido(id);
    }
}
