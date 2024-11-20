package com.example.mse.monolito.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class NatsEventPublisher {

	@Autowired
    private Connection natsConnection;
	
	@Autowired
    private ObjectMapper objectMapper;

    public void publish(String subject, Object payload) {
        try {
        	String message = objectMapper.writeValueAsString(payload);
            natsConnection.publish(subject, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[NATS] evento publicado " + subject + ": " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void subscribe(String topic, MessageHandler handler) throws Exception {
		Dispatcher dispatcher = natsConnection.createDispatcher(handler);
		dispatcher.subscribe(topic);
	}
    
    public Map<String, Object> getPayload(Message msg) throws Exception {
		String json = new String(msg.getData());
		return objectMapper.readValue(json, Map.class);
	}

   
}
