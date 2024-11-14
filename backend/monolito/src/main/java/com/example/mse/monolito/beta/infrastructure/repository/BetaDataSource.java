package com.example.mse.monolito.beta.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.example.mse.monolito.beta.domain.Beta;

public interface BetaDataSource {
    List<Beta> findAll();
    Optional<Beta> findById(Long id);
    Beta save(Beta beta);
    void deleteById(Long id);
}
