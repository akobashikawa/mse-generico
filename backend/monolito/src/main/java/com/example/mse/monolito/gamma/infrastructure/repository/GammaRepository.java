package com.example.mse.monolito.gamma.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mse.monolito.gamma.domain.Gamma;

public interface GammaRepository extends JpaRepository<Gamma, Long> {

    // Puedes definir consultas personalizadas si lo necesitas, por ejemplo:
    // List<Gamma> findByTexto(String texto);
	List<Gamma> findByAlfaId(Long alfaId);
}