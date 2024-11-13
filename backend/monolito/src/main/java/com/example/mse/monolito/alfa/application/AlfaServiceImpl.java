package com.example.mse.monolito.alfa.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.alfa.domain.AlfaService;
import com.example.mse.monolito.alfa.infrastructure.AlfaRepository;

@Service
public class AlfaServiceImpl implements AlfaService {

    private final AlfaRepository alfaRepository;

    public AlfaServiceImpl(AlfaRepository alfaRepository) {
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
