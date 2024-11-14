package com.example.mse.monolito.beta.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mse.monolito.beta.domain.Beta;

public interface BetaRepository extends JpaRepository<Beta, Long> {
    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Beta> findByTexto(String texto);
}