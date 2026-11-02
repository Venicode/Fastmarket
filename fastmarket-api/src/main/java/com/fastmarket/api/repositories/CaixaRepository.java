package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Caixa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;


public interface CaixaRepository extends JpaRepository<Caixa, Long> {
    Optional<Caixa> findFirstByDataFechamentoIsNullOrderByIdDesc();
}
