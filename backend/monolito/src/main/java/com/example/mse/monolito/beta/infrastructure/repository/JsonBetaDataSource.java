package com.example.mse.monolito.beta.infrastructure.repository;

import com.example.mse.monolito.beta.domain.Beta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JsonBetaDataSource implements BetaDataSource {

    private final ObjectMapper objectMapper;
    private final File jsonFile = new File("data/beta.json");

    public JsonBetaDataSource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Beta> findAll() {
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<Beta>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    @Override
    public Optional<Beta> findById(Long id) {
        return findAll().stream().filter(beta -> beta.getId().equals(id)).findFirst();
    }

    @Override
    public Beta save(Beta beta) {
        throw new UnsupportedOperationException("Save operation not supported for JSON data source");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Delete operation not supported for JSON data source");
    }
}
