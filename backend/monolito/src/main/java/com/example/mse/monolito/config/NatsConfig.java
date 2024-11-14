package com.example.mse.monolito.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.nats.client.Connection;
import io.nats.client.Nats;

@Configuration
public class NatsConfig {
	
	@Bean
    Connection natsConnection(@Value("${nats.url:nats://localhost:4222}") String host) throws Exception {
        return Nats.connect(host);
    }

}
