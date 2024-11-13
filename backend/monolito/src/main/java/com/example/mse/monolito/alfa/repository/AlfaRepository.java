package com.example.mse.monolito.alfa.repository;

import com.example.mse.monolito.alfa.domain.Alfa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlfaRepository extends JpaRepository<Alfa, Long> {
    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Alfa> findByTexto(String texto);
}