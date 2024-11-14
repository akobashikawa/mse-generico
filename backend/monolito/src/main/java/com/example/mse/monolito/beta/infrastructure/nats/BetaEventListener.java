package com.example.mse.monolito.beta.infrastructure.nats;

import org.springframework.stereotype.Component;

import com.example.mse.monolito.beta.application.BetaService;
import com.example.mse.monolito.nats.NatsEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BetaEventListener {

    private final BetaService betaService;

    public BetaEventListener(BetaService betaService, NatsEventPublisher natsEventPublisher) {
        natsEventPublisher.subscribe("gamma.created", this::handleGammaCreatedEvent);
        this.betaService = betaService;
    }

    private void handleGammaCreatedEvent(String message) {
        // Parsear el JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode event = objectMapper.readTree(message);
            Long betaId = event.get("betaId").asLong();
            int gammaEntero = event.get("entero").asInt();

            // Actualizar el valor entero de Beta
            betaService.updateEntero(betaId, gammaEntero);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
