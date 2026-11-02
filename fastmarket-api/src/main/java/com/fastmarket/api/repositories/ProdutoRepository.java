package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.domains.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findProdutoById(Long id);
    @Modifying
    @Query("""
            update Produto p
            set p.isAtivo = false
            where p.id = :id
            """)
    void disableProduto(Long id);

    Optional<Produto> findProdutoByCodigoBarras(Long codigoBarras);
}
