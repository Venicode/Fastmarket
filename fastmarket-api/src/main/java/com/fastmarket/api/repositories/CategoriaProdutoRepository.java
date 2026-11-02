package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.CategoriaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
    Optional<CategoriaProduto> findCategoriaProdutoById(Long id);
    @Modifying
    @Query("""
            delete CategoriaProduto p
            where p.id = :id
            """)
    void deleteCategoriaProduto(Long id);
}
