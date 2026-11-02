package com.fastmarket.api.services;

import com.fastmarket.api.domains.*;
import com.fastmarket.api.repositories.PedidoRepository;
import com.fastmarket.api.dtos.PedidoDto;
import com.fastmarket.api.repositories.StatusPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private CaixaService caixaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private MetodoPagamentoService metodoPagamentoService;
    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    public Pedido createPedido(PedidoDto pedidoDto) throws Exception {
        Caixa caixa = this.caixaService.buscarAberto()
                .orElseThrow(() -> new IllegalStateException("Abra o caixa antes de realizar vendas."));

        Pedido pedido = new Pedido();
        pedido.setCaixa(caixa);
        pedido.setCliente(pedidoDto.cliente());
        MetodoPagamento metodoPagamento = this.metodoPagamentoService.findMetodoPagamentoById(pedidoDto.metodoPagamento().getId());
        pedido.setMetodoPagamento(metodoPagamento);
        pedido.setDataVenda(LocalDateTime.now());
        pedido.setEnderecoEntrega(pedidoDto.enderecoEntrega());
        pedido.setStatusPedido(pedidoDto.statusPedido());
        pedido.setTipoPedido(pedidoDto.tipoPedido());
        pedido.setEntregador(pedidoDto.entregador());
        this.pedidoRepository.save(pedido);
        return pedido;
    }

    public Pedido findPedidoById(Long id) {
        return this.pedidoRepository.findPedidoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido not found."));
    }

    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidoList = this.pedidoRepository.findAll();
        return new ResponseEntity<>(pedidoList, HttpStatus.OK);
    }


    public List<Pedido> listarPorTipoEStatus(String tipo, String status) {
        return pedidoRepository.findByTipoPedido_TipoPedidoIgnoreCaseAndStatusPedido_StatusIgnoreCase(tipo, status);
    }

    public void atualizarStatus(Long pedidoId, Long statusId) {
        Pedido pedido = findPedidoById(pedidoId);
        StatusPedido status = statusPedidoRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Status não encontrado."));
        pedido.setStatusPedido(status);
        pedidoRepository.save(pedido);
    }
}