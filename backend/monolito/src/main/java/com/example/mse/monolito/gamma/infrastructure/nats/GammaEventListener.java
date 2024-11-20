package com.example.mse.monolito.gamma.infrastructure.nats;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mse.monolito.gamma.application.GammaService;
import com.example.mse.monolito.nats.NatsEventPublisher;
import io.nats.client.Message;
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
		eventPublisher.subscribe("alfa.delete", this::handleAlfaDelete);
		eventPublisher.subscribe("beta.created", this::handleBetaCreated);
	}

	private void handleAlfaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("[NATS] gamma manejando alfa.created: " + payload);

			Long id = ((Number) payload.get("id")).longValue();
			String texto = (String) payload.get("texto");
			Integer entero = (Integer) payload.get("entero");
			Double decimal = (Double) payload.get("decimal");

			System.out.println("id: " + id);
			System.out.println("texto: " + texto);
			System.out.println("entero: " + entero);
			System.out.println("decimal: " + decimal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleBetaCreated(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("[NATS] gamma manejando beta.created: " + payload);

			Long id = ((Number) payload.get("id")).longValue();
			String texto = (String) payload.get("texto");
			Integer entero = (Integer) payload.get("entero");
			Double decimal = (Double) payload.get("decimal");

			System.out.println("id: " + id);
			System.out.println("texto: " + texto);
			System.out.println("entero: " + entero);
			System.out.println("decimal: " + decimal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleAlfaDelete(Message msg) {
		try {
			Map<String, Object> payload = eventPublisher.getPayload(msg);
			System.out.println("[NATS] gamma manejando alfa.delete: " + payload);

			Long id = ((Number) payload.get("id")).longValue();

			System.out.println("id: " + id);

			// Eliminaciones BEGIN
			// Eliminaciones END

			Map<String, Object> responsePayload = new HashMap<>();
			responsePayload.put("id", id);
			responsePayload.put("status", "deleted");

			// Publicar la respuesta en el replySubject
			eventPublisher.publish(msg.getReplyTo(), responsePayload); // Usamos msg.getReplyTo() para saber a dónde responder
			System.out.println("[NATS] respuesta publicada para " + msg.getReplyTo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
