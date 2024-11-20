package com.example.mse.monolito.gamma.infrastructure.repository;

import com.example.mse.monolito.gamma.domain.Gamma;

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
    public Optional<Gamma> findByAlfaId(Long alfaId) {
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
    
    @Override
    public void deleteByAlfaId(Long alfaId) {
    	List<Gamma> itemsToDelete = gammaRepository.findByAlfaId(alfaId);
    	
    	if (itemsToDelete.isEmpty()) {
            System.out.println("[GammaService] No se encontraron items con el alfaId: " + alfaId);
            return;
        }
    	
    	for (Gamma item : itemsToDelete) {
            gammaRepository.delete(item);
            System.out.println("[GammaService] Item eliminado: " + item);
        }
    }
}
