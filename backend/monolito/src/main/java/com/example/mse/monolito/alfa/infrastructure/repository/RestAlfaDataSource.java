package com.example.mse.monolito.alfa.infrastructure.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.mse.monolito.alfa.domain.Alfa;

import java.util.List;
import java.util.Optional;

@Component
public class RestAlfaDataSource implements AlfaDataSource {

    private final RestTemplate restTemplate;
    private final String apiUrl; // URL del API REST externo

    public RestAlfaDataSource(RestTemplate restTemplate,
    		@Value("${alfa.datasource.rest.url:}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<Alfa> findAll() {
        Alfa[] alfases = restTemplate.getForObject(apiUrl, Alfa[].class);
        return List.of(alfases);
    }

    @Override
    public Optional<Alfa> findById(Long id) {
        Alfa alfa = restTemplate.getForObject(apiUrl + "/" + id, Alfa.class);
        return Optional.ofNullable(alfa);
    }

    @Override
    public Alfa save(Alfa alfa) {
        return restTemplate.postForObject(apiUrl, alfa, Alfa.class);
    }

    @Override
    public void deleteById(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}

