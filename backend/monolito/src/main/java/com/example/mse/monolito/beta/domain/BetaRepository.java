package com.example.mse.monolito.beta.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BetaRepository extends JpaRepository<Beta, Long> {
    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Beta> findByTexto(String texto);
}