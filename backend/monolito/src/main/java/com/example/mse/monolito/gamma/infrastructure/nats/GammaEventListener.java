package com.example.mse.monolito.gamma.infrastructure.nats;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mse.monolito.gamma.application.GammaService;
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
public class GammaEventListener {
	
	@Autowired
	private NatsEventPublisher eventPublisher;
	
	@Autowired
    private GammaService gammaService;
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println("[NATS] gamma escuchando");
		eventPublisher.subscribe("alfa.created", this::handleAlfaCreated);
		eventPublisher.subscribe("beta.created", this::handleBetaCreated);
	}

	private void handleAlfaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("[NATS] gamma manejando alfa.created: " + payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleBetaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("gamma manejando beta.created: " + payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
