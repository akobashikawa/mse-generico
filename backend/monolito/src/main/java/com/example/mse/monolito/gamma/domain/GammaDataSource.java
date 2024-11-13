package com.example.mse.monolito.gamma.domain;

import java.util.List;
import java.util.Optional;

public interface GammaDataSource {
    List<Gamma> findAll();
    Optional<Gamma> findById(Long id);
    Gamma save(Gamma gamma);
    void deleteById(Long id);
}
