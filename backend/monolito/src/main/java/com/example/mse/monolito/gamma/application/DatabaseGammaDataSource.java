package com.example.mse.monolito.gamma.application;

import com.example.mse.monolito.gamma.domain.Gamma;
import com.example.mse.monolito.gamma.domain.GammaDataSource;
import com.example.mse.monolito.gamma.domain.GammaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DatabaseGammaDataSource implements GammaDataSource {

    private final GammaRepository gammaRepository;

    public DatabaseGammaDataSource(GammaRepository gammaRepository) {
        this.gammaRepository = gammaRepository;
    }

    @Override
    public List<Gamma> findAll() {
        return gammaRepository.findAll();
    }

    @Override
    public Optional<Gamma> findById(Long id) {
        return gammaRepository.findById(id);
    }

    @Override
    public Gamma save(Gamma gamma) {
        return gammaRepository.save(gamma);
    }

    @Override
    public void deleteById(Long id) {
        gammaRepository.deleteById(id);
    }
}
