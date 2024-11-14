package com.example.mse.monolito.alfa.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mse.monolito.alfa.domain.Alfa;

public interface AlfaRepository extends JpaRepository<Alfa, Long> {
    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Alfa> findByTexto(String texto);
}