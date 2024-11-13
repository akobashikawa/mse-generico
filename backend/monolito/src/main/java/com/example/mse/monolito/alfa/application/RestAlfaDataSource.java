package com.example.mse.monolito.alfa.application;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.alfa.domain.AlfaDataSource;

import java.util.List;
import java.util.Optional;

@Component
public class RestAlfaDataSource implements AlfaDataSource {

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8080/api/beta"; // URL del API REST externo

    public RestAlfaDataSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Alfa> findAll() {
        // Aquí puedes hacer una solicitud GET para obtener todos los objetos "Alfa"
        Alfa[] alfases = restTemplate.getForObject(apiUrl, Alfa[].class);
        return List.of(alfases);
    }

    @Override
    public Optional<Alfa> findById(Long id) {
        // Llamada a un API para obtener un único "Alfa" por ID
        Alfa alfa = restTemplate.getForObject(apiUrl + "/" + id, Alfa.class);
        return Optional.ofNullable(alfa);
    }

    @Override
    public Alfa save(Alfa alfa) {
        // Llamada POST para crear un nuevo "Alfa" en el API
        return restTemplate.postForObject(apiUrl, alfa, Alfa.class);
    }

    @Override
    public void deleteById(Long id) {
        // Llamada DELETE para eliminar el "Alfa" por ID
        restTemplate.delete(apiUrl + "/" + id);
    }
}

