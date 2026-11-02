package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Acesso;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {
    Optional<Acesso> findAcessoById(Long id);
    Optional<Acesso> findAcessoByAcesso(String acessoDescricao);
    @Modifying
    @Query("""
            delete Acesso m
            where m.id = :id
            """)
    void deleteAcesso(Long id);
}
