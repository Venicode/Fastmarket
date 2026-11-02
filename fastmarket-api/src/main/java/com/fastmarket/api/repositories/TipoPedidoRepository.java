package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.TipoPedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface TipoPedidoRepository extends JpaRepository<TipoPedido, Long> {
    Optional<TipoPedido> findTipoPedidoById(Long id);
    Optional<TipoPedido> findTipoPedidoByTipoPedido(String tipoPedido);
    @Modifying
    @Query("""
            delete TipoPedido m
            where m.id = :id
            """)
    void deleteTipoPedido(Long id);
}
