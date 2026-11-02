package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findClienteById(Long id);
    @Modifying
    @Query("""
            update Cliente c
            set c.ativo = false
            where c.id = :id
            """)
    void disableCliente(Long id);
}
