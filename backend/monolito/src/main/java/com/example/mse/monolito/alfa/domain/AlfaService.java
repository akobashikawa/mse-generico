package com.example.mse.monolito.alfa.domain;

import java.util.List;
import java.util.Optional;

public interface AlfaService {
    List<Alfa> findAll();
    Optional<Alfa> findById(Long id);
    Alfa save(Alfa alfa);
    void deleteById(Long id);
}
