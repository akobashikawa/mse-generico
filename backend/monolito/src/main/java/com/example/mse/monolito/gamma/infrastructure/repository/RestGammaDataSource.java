package com.example.mse.monolito.gamma.infrastructure.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.mse.monolito.gamma.domain.Gamma;

import java.util.List;
import java.util.Optional;

@Component
public class RestGammaDataSource implements GammaDataSource {

    private final RestTemplate restTemplate;
    private final String apiUrl; // URL del API REST externo

    public RestGammaDataSource(RestTemplate restTemplate,
    		@Value("${gamma.datasource.rest.url:}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<Gamma> findAll() {
        Gamma[] gammases = restTemplate.getForObject(apiUrl, Gamma[].class);
        return List.of(gammases);
    }

    @Override
    public Optional<Gamma> findById(Long id) {
        Gamma gamma = restTemplate.getForObject(apiUrl + "/" + id, Gamma.class);
        return Optional.ofNullable(gamma);
    }
    
    @Override
    public Optional<Gamma> findByAlfaId(Long alfaId) {
    	Gamma gamma = restTemplate.getForObject(apiUrl + "/alfa_id/" + alfaId, Gamma.class);
    	return Optional.ofNullable(gamma);
    }

    @Override
    public Gamma save(Gamma gamma) {
        return restTemplate.postForObject(apiUrl, gamma, Gamma.class);
    }

    @Override
    public void deleteById(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
    
    @Override
    public void deleteByAlfaId(Long alfaId) {
    	restTemplate.delete(apiUrl + "/alfa_id/" + alfaId);
    }
}

