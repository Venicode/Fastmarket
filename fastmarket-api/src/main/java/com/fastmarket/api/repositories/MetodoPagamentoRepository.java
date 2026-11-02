package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.MetodoPagamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    Optional<MetodoPagamento> findMetodoPagamentoById(Long id);
    Optional<MetodoPagamento> findMetodoPagamentoByMetodoPagamento(String metodoPagamentoDescricao);
    @Modifying
    @Query("""
            delete MetodoPagamento m
            where m.id = :id
            """)
    void deleteMetodoPagamento(Long id);
}
