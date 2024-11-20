package com.example.mse.monolito.beta.infrastructure.nats;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mse.monolito.beta.application.BetaService;
import com.example.mse.monolito.nats.NatsEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import jakarta.annotation.PostConstruct;

@Component
public class BetaEventListener {
	
	@Autowired
	private NatsEventPublisher eventPublisher;
	
	@Autowired
    private BetaService betaService;
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println("beta escuchando NATS");
		eventPublisher.subscribe("gamma.created", this::handleGammaCreated);
	}

	private void handleGammaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("beta manejando gamma.created: " + payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
