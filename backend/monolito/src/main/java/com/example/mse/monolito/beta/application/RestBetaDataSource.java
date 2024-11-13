package com.example.mse.monolito.beta.application;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.mse.monolito.beta.domain.Beta;
import com.example.mse.monolito.beta.domain.BetaDataSource;

import java.util.List;
import java.util.Optional;

@Component
public class RestBetaDataSource implements BetaDataSource {

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8080/beta"; // URL del API REST externo

    public RestBetaDataSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Beta> findAll() {
        // Aquí puedes hacer una solicitud GET para obtener todos los objetos "Beta"
        Beta[] betases = restTemplate.getForObject(apiUrl, Beta[].class);
        return List.of(betases);
    }

    @Override
    public Optional<Beta> findById(Long id) {
        // Llamada a un API para obtener un único "Beta" por ID
        Beta beta = restTemplate.getForObject(apiUrl + "/" + id, Beta.class);
        return Optional.ofNullable(beta);
    }

    @Override
    public Beta save(Beta beta) {
        // Llamada POST para crear un nuevo "Beta" en el API
        return restTemplate.postForObject(apiUrl, beta, Beta.class);
    }

    @Override
    public void deleteById(Long id) {
        // Llamada DELETE para eliminar el "Beta" por ID
        restTemplate.delete(apiUrl + "/" + id);
    }
}

