package com.example.mse.monolito.gamma.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GammaRepository extends JpaRepository<Gamma, Long> {
    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Gamma> findByTexto(String texto);
}