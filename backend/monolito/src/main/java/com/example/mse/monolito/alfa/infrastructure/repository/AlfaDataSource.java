package com.example.mse.monolito.alfa.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.example.mse.monolito.alfa.domain.Alfa;

public interface AlfaDataSource {
    List<Alfa> findAll();
    Optional<Alfa> findById(Long id);
    Alfa save(Alfa alfa);
    void deleteById(Long id);
}
