package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findPedidoById(Long id);
    List<Pedido> findByTipoPedido_TipoPedidoIgnoreCaseAndStatusPedido_StatusIgnoreCase(String tipo, String status);
}
