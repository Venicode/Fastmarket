package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.StatusPedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Long> {
    Optional<StatusPedido> findStatusPedidoById(Long id);
    Optional<StatusPedido> findStatusPedidoByStatus(String status);
    @Modifying
    @Query("""
            delete StatusPedido m
            where m.id = :id
            """)
    void deleteStatusPedido(Long id);
}
