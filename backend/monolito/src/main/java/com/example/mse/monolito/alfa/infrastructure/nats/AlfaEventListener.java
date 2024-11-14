package com.example.mse.monolito.alfa.infrastructure.nats;

import org.springframework.stereotype.Component;

import com.example.mse.monolito.alfa.application.AlfaService;
import com.example.mse.monolito.nats.NatsEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AlfaEventListener {

    private final AlfaService alfaService;

    public AlfaEventListener(AlfaService alfaService, NatsEventPublisher natsEventPublisher) {
        natsEventPublisher.subscribe("gamma.created", this::handleGammaCreatedEvent);
        this.alfaService = alfaService;
    }

    private void handleGammaCreatedEvent(String message) {
        // Parsear el JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode event = objectMapper.readTree(message);
            Long alfaId = event.get("alfaId").asLong();
            int gammaEntero = event.get("entero").asInt();

            // Actualizar el valor entero de Alfa
            alfaService.updateEntero(alfaId, gammaEntero);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
