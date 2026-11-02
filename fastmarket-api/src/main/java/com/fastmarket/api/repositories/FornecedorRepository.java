package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Fornecedor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findFornecedorById(Long id);
    @Modifying
    @Query("""
            update Fornecedor s
            set s.isAtivo = false
            where s.id = :id
            """)
    void disableFornecedor(Long id);
}
