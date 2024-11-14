package com.example.mse.monolito.alfa.infrastructure.repository;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JsonAlfaDataSource implements AlfaDataSource {

    private final ObjectMapper objectMapper;
    private final File jsonFile = new File("data/alfa.json");

    public JsonAlfaDataSource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Alfa> findAll() {
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<List<Alfa>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    @Override
    public Optional<Alfa> findById(Long id) {
        return findAll().stream().filter(alfa -> alfa.getId().equals(id)).findFirst();
    }

    @Override
    public Alfa save(Alfa alfa) {
        throw new UnsupportedOperationException("Save operation not supported for JSON data source");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Delete operation not supported for JSON data source");
    }
}
