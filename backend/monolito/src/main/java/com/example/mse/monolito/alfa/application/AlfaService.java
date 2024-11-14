package com.example.mse.monolito.alfa.application;

import java.util.List;
import java.util.Optional;

import com.example.mse.monolito.alfa.domain.Alfa;

public interface AlfaService {
    List<Alfa> findAll();
    Optional<Alfa> findById(Long id);
    Alfa save(Alfa alfa);
    void deleteById(Long id);
    
    Alfa updateEntero(Long id, Integer nuevoEntero);
}
