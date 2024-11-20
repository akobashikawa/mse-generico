package com.example.mse.monolito.gamma.application;

import com.example.mse.monolito.gamma.domain.Gamma;
import com.example.mse.monolito.gamma.infrastructure.repository.DatabaseGammaDataSource;
import com.example.mse.monolito.gamma.infrastructure.repository.GammaDataSource;
import com.example.mse.monolito.gamma.infrastructure.repository.JsonGammaDataSource;
import com.example.mse.monolito.gamma.infrastructure.repository.RestGammaDataSource;
import com.example.mse.monolito.nats.NatsEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GammaServiceImpl implements GammaService {

	@Autowired
	private NatsEventPublisher eventPublisher;

	private final GammaDataSource dataSource;

    public GammaServiceImpl(
        DatabaseGammaDataSource databaseGammaDataSource,
        JsonGammaDataSource jsonGammaDataSource,
        RestGammaDataSource restGammaDataSource,
        @Value("${gamma.datasource.type:database}") String dataSourceType
    ) {
    	switch (dataSourceType) {
	        case "database":
	            this.dataSource = databaseGammaDataSource;
	            break;
	        case "json":
	            this.dataSource = jsonGammaDataSource;
	            break;
	        case "rest":
	            this.dataSource = restGammaDataSource;
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown data source type: " + dataSourceType);
	    }
    }

    public List<Gamma> findAll() {
        return dataSource.findAll();
    }

    public Optional<Gamma> findById(Long id) {
        return dataSource.findById(id);
    }

    public Gamma save(Gamma gamma) {
    	Gamma savedItem = dataSource.save(gamma);
    	
    	// Publicar evento en NATS
    	Map<String, Object> payload = new HashMap<>();
    	payload.put("id", savedItem.getId());
    	payload.put("entero", savedItem.getEntero());
    	payload.put("decimal", savedItem.getDecimal());
    	payload.put("alfa_id", savedItem.getAlfa().getId());
    	payload.put("beta_id", savedItem.getBeta().getId());

        eventPublisher.publish("gamma.created", payload);
        
    	return savedItem;
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
}
