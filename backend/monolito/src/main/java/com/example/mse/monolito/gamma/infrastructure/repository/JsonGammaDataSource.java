package com.example.mse.monolito.gamma.infrastructure.repository;

import com.example.mse.monolito.gamma.domain.Gamma;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JsonGammaDataSource implements GammaDataSource {

    private final ObjectMapper objectMapper;
    private final File jsonFile = new File("data/gamma.json");

    public JsonGammaDataSource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Gamma> findAll() {
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<Gamma>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    @Override
    public Optional<Gamma> findById(Long id) {
        return findAll().stream().filter(gamma -> gamma.getId().equals(id)).findFirst();
    }
    
    @Override
    public Optional<Gamma> findByAlfaId(Long alfaId) {
    	return findAll().stream().filter(gamma -> gamma.getAlfa().getId().equals(alfaId)).findFirst();
    }

    @Override
    public Gamma save(Gamma gamma) {
        throw new UnsupportedOperationException("Save operation not supported for JSON data source");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Delete operation not supported for JSON data source");
    }
    
    @Override
    public void deleteByAlfaId(Long alfaId) {
    	throw new UnsupportedOperationException("Delete operation not supported for JSON data source");
    }
}
