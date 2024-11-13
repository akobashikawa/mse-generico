package com.example.mse.monolito.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class NatsEventPublisher {

    private final Connection natsConnection;
    private final ObjectMapper objectMapper;

    public NatsEventPublisher(Connection natsConnection) {
        this.natsConnection = natsConnection;
        this.objectMapper = new ObjectMapper();
    }

    public void publish(String subject, Object payload) {
        try {
        	String message = objectMapper.writeValueAsString(payload);
            natsConnection.publish(subject, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Evento NATS publicado " + subject + ": " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String subject, MessageHandler messageHandler) {
        Dispatcher dispatcher = natsConnection.createDispatcher(msg -> {
            String response = new String(msg.getData(), StandardCharsets.UTF_8);
            messageHandler.handleMessage(response);
        });
        dispatcher.subscribe(subject);
    }

    /**
     * Responde a solicitudes enviadas en un tema.
     * @param subject Tema al que se responde.
     * @param responder Función que maneja la solicitud y devuelve la respuesta.
     */
    public void reply(String subject, RequestHandler responder) {
        Dispatcher dispatcher = natsConnection.createDispatcher(msg -> {
            String request = new String(msg.getData(), StandardCharsets.UTF_8);
            String response = responder.handleRequest(request);
            try {
                natsConnection.publish(msg.getReplyTo(), response.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dispatcher.subscribe(subject);
    }

    /**
     * Cierra la conexión con NATS.
     */
    public void close() {
        try {
            natsConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface MessageHandler {
        void handleMessage(String message);
    }

    @FunctionalInterface
    public interface RequestHandler {
        String handleRequest(String request);
    }
}
