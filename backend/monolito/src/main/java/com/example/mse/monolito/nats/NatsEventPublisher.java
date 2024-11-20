package com.example.mse.monolito.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class NatsEventPublisher {

	@Autowired
	private Connection natsConnection;

	@Autowired
	private ObjectMapper objectMapper;

	// Publicar un evento en NATS
	public void publish(String subject, Object payload) {
		try {
			String message = objectMapper.writeValueAsString(payload);
			natsConnection.publish(subject, message.getBytes(StandardCharsets.UTF_8));
			System.out.println("[NATS] evento publicado " + subject + ": " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método auxiliar para publicar un mensaje con un replyTo específico
	private void publish(String subject, Map<String, Object> payload, String replySubject) {
		try {
			String message = objectMapper.writeValueAsString(payload);
			natsConnection.publish(subject, replySubject, message.getBytes(StandardCharsets.UTF_8));
			System.out.println("[NATS] evento publicado con reply: " + subject + ", replySubject: " + replySubject + ": " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Suscribirse a un evento en NATS
	public void subscribe(String topic, MessageHandler handler) throws Exception {
		Dispatcher dispatcher = natsConnection.createDispatcher(handler);
		dispatcher.subscribe(topic);
	}

	// Método para manejar el request/reply, publicando la solicitud y esperando la respuesta
	public CompletableFuture<Message> reply(String subject, Map<String, Object> payload) {
		CompletableFuture<Message> futureResponse = new CompletableFuture<>();

		try {
			// Generar un canal único para la respuesta
			String replySubject = subject + ".response." + System.nanoTime();
			System.out.println("[NATS] alfa.delete replySubject: " + replySubject);

			// Suscribirse al canal de respuesta (replySubject)
			subscribe(replySubject, msg -> {
				System.out.println("[NATS] respuesta recibida para " + replySubject);
				futureResponse.complete(msg);
	        });
			
			// Publicar la solicitud con un campo replyTo
			publish(subject, payload, replySubject);

		} catch (Exception e) {
			futureResponse.completeExceptionally(e);
		}

		return futureResponse;
	}

	// Método para obtener el payload de un mensaje NATS
	public Map<String, Object> getPayload(Message msg) throws Exception {
		String jsonPayload = new String(msg.getData(), StandardCharsets.UTF_8);
		Map<String, Object> payload = objectMapper.readValue(jsonPayload, Map.class);

		return payload;
	}

}
