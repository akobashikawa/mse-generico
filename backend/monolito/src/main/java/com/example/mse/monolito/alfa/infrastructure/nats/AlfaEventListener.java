package com.example.mse.monolito.alfa.infrastructure.nats;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mse.monolito.alfa.application.AlfaService;
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
public class AlfaEventListener {
	
	@Autowired
	private NatsEventPublisher eventPublisher;

	@Autowired
    private AlfaService alfaService;
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println("[NATS] alfa escuchando");
		eventPublisher.subscribe("gamma.created", this::handleGammaCreated);
	}

	private void handleGammaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			
			System.out.println("[NATS] alfa manejando gamma.created: " + payload);
			
			Long id = ((Number) payload.get("id")).longValue();
			String texto = (String) payload.get("texto");
	        Integer entero = (Integer) payload.get("entero");
	        Double decimal = (Double) payload.get("decimal");
	        Long alfaId = ((Number) payload.get("alfa_id")).longValue();
	        Long betaId = ((Number) payload.get("beta_id")).longValue();
			
	        System.out.println("id: " + id);
	        System.out.println("texto: " + texto);
	        System.out.println("entero: " + entero);
	        System.out.println("decimal: " + decimal);
	        System.out.println("alfa_id: " + alfaId);
	        System.out.println("beta_id: " + betaId);
	        
	        alfaService.updateEntero(alfaId, entero);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
