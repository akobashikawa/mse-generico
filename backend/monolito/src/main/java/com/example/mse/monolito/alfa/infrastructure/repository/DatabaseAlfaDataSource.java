package com.example.mse.monolito.alfa.infrastructure.repository;

import com.example.mse.monolito.alfa.domain.Alfa;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DatabaseAlfaDataSource implements AlfaDataSource {

    private final AlfaRepository alfaRepository;

    public DatabaseAlfaDataSource(AlfaRepository alfaRepository) {
        this.alfaRepository = alfaRepository;
    }

    @Override
    public List<Alfa> findAll() {
        return alfaRepository.findAll();
    }

    @Override
    public Optional<Alfa> findById(Long id) {
        return alfaRepository.findById(id);
    }

    @Override
    public Alfa save(Alfa alfa) {
        return alfaRepository.save(alfa);
    }

    @Override
    public void deleteById(Long id) {
        alfaRepository.deleteById(id);
    }
}
