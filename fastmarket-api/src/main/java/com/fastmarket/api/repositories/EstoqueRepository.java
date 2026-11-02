package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Produto;
import com.fastmarket.api.domains.Estoque;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    @Modifying
    @Query("""
            update Estoque s
            set s.quantidade = :quantidade
            where s.produto = :produto
            """)
    void updateQuantidade(int quantidade, Produto produto);
}
