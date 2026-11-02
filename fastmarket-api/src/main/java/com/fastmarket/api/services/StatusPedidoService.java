package com.fastmarket.api.services;

import com.fastmarket.api.domains.StatusPedido;
import com.fastmarket.api.dtos.StatusPedidoDto;
import com.fastmarket.api.repositories.StatusPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusPedidoService {
    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    public StatusPedido createStatusPedido(StatusPedidoDto statusPedidoDto){
        StatusPedido newStatusPedido = new StatusPedido();
        newStatusPedido.setStatus(statusPedidoDto.status());
        this.statusPedidoRepository.save(newStatusPedido);
        return newStatusPedido;
    }
    public StatusPedido updateStatusPedido(StatusPedidoDto statusPedidoDto, Long id) throws Exception {
        StatusPedido newStatusPedido = findStatusPedidoById(id);
        newStatusPedido.setStatus(statusPedidoDto.status());
        this.statusPedidoRepository.save(newStatusPedido);
        return newStatusPedido;
    }
    public StatusPedido findStatusPedidoById(Long id) throws Exception {
        return this.statusPedidoRepository.findStatusPedidoById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order Status not found."));
    }

    public StatusPedido findStatusPedidoByStatusPedidoDescription(String status) throws Exception {
        return this.statusPedidoRepository.findStatusPedidoByStatus(status)
                .orElseThrow(()-> new EntityNotFoundException("Order Status not found."));
    }

    public ResponseEntity<List<StatusPedido>> getAllStatusPedido() {
        List<StatusPedido> statusPedidoList = this.statusPedidoRepository.findAll();
        return  new ResponseEntity<>(statusPedidoList, HttpStatus.OK);
    }
    public void deleteStatusPedido(Long id){
        this.statusPedidoRepository.deleteStatusPedido(id);
    }
}
