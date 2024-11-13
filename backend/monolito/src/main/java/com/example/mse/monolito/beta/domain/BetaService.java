package com.example.mse.monolito.beta.domain;

import java.util.List;
import java.util.Optional;

public interface BetaService {
    List<Beta> findAll();
    Optional<Beta> findById(Long id);
    Beta save(Beta beta);
    void deleteById(Long id);
}
