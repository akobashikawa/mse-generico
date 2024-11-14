package com.example.mse.monolito.gamma.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.example.mse.monolito.gamma.domain.Gamma;

public interface GammaDataSource {
    List<Gamma> findAll();
    Optional<Gamma> findById(Long id);
    Gamma save(Gamma gamma);
    void deleteById(Long id);
}
