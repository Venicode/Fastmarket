package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.Login;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findLoginByMatricula(int matricula);
    Optional<Login> findLoginById(Long id);
}
