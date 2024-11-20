package com.example.mse.monolito.alfa.application;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.alfa.infrastructure.repository.AlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.DatabaseAlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.JsonAlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.RestAlfaDataSource;
import com.example.mse.monolito.beta.domain.Beta;
import com.example.mse.monolito.gamma.infrastructure.repository.GammaDataSource;
import com.example.mse.monolito.nats.NatsEventPublisher;

import io.nats.client.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AlfaServiceImpl implements AlfaService {

	@Autowired
	private NatsEventPublisher eventPublisher;

	private final AlfaDataSource dataSource;

	public AlfaServiceImpl(DatabaseAlfaDataSource databaseAlfaDataSource, JsonAlfaDataSource jsonAlfaDataSource,
			RestAlfaDataSource restAlfaDataSource, @Value("${alfa.datasource.type:database}") String dataSourceType) {
		switch (dataSourceType) {
		case "database":
			this.dataSource = databaseAlfaDataSource;
			break;
		case "json":
			this.dataSource = jsonAlfaDataSource;
			break;
		case "rest":
			this.dataSource = restAlfaDataSource;
			break;
		default:
			throw new IllegalArgumentException("Unknown data source type: " + dataSourceType);
		}
	}

	public List<Alfa> findAll() {
		return dataSource.findAll();
	}

	public Optional<Alfa> findById(Long id) {
		return dataSource.findById(id);
	}

	public Alfa save(Alfa alfa) {
		Alfa savedItem = dataSource.save(alfa);

		// Publicar evento en NATS
		Map<String, Object> payload = new HashMap<>();
		payload.put("id", savedItem.getId());
		payload.put("texto", savedItem.getTexto());
		payload.put("entero", savedItem.getEntero());
		payload.put("decimal", savedItem.getDecimal());

		eventPublisher.publish("alfa.created", payload);

		return savedItem;
	}

	public void deleteById(Long id) {

		Map<String, Object> payload = new HashMap<>();
		payload.put("id", id);

		CompletableFuture<Message> deleteResponse = eventPublisher.reply("alfa.delete", payload);

		deleteResponse.thenAccept(response -> {
			try {
				// Procesar la respuesta cuando llegue
				String responsePayload = new String(response.getData(), StandardCharsets.UTF_8);
				System.out.println("[NATS] alfa.delete respuesta recibida: " + responsePayload);

				// Si la respuesta de NATS confirma la eliminación, eliminar el elemento
				dataSource.deleteById(id);
			} catch (Exception e) {
				System.err.println("[NATS] Error procesando la respuesta de NATS: " + e.getMessage());
			}
		}).exceptionally(ex -> {
			// Manejo de errores si ocurre una excepción al esperar la respuesta
			System.err.println("[ERROR] Error en la solicitud delete: " + ex.getMessage());
			return null;
		});
	}

	@Override
	public Alfa updateEntero(Long id, Integer entero) {
		Alfa found = dataSource.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Alfa no encontrado con id: " + id));

		Integer nuevoEntero = found.getEntero() + entero;
		found.setEntero(nuevoEntero);

		return dataSource.save(found);
	}
}
