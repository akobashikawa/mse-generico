package com.example.mse.monolito.beta.application;

import java.util.List;
import java.util.Optional;

import com.example.mse.monolito.beta.domain.Beta;

public interface BetaService {
    List<Beta> findAll();
    Optional<Beta> findById(Long id);
    Beta save(Beta beta);
    void deleteById(Long id);
    
    Beta updateEntero(Long id, Integer nuevoEntero);
}
