package com.example.mse.monolito.beta.infrastructure.repository;

import com.example.mse.monolito.beta.domain.Beta;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DatabaseBetaDataSource implements BetaDataSource {

    private final BetaRepository betaRepository;

    public DatabaseBetaDataSource(BetaRepository betaRepository) {
        this.betaRepository = betaRepository;
    }

    @Override
    public List<Beta> findAll() {
        return betaRepository.findAll();
    }

    @Override
    public Optional<Beta> findById(Long id) {
        return betaRepository.findById(id);
    }

    @Override
    public Beta save(Beta beta) {
        return betaRepository.save(beta);
    }

    @Override
    public void deleteById(Long id) {
        betaRepository.deleteById(id);
    }
}
