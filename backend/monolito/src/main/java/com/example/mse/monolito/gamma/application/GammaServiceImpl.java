package com.example.mse.monolito.gamma.application;

import com.example.mse.monolito.gamma.domain.Gamma;
import com.example.mse.monolito.gamma.domain.GammaDataSource;
import com.example.mse.monolito.gamma.domain.GammaService;
import com.example.mse.monolito.nats.NatsEventPublisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GammaServiceImpl implements GammaService {

    private final GammaDataSource dataSource;
    private final NatsEventPublisher eventPublisher;

    public GammaServiceImpl(
        DatabaseGammaDataSource databaseGammaDataSource,
        JsonGammaDataSource jsonGammaDataSource,
        RestGammaDataSource restGammaDataSource,
        @Value("${gamma.datasource.type:database}") String dataSourceType,
        NatsEventPublisher eventPublisher
    ) {
    	this.eventPublisher = eventPublisher;
    	
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
    	Gamma savedGamma = dataSource.save(gamma);
    	
    	// Publicar evento en NATS
        String eventPayload = String.format(
            "{\"gammaId\": %d, \"alfaId\": %d, \"betaId\": %d, \"entero\": %d}",
            savedGamma.getId(),
            savedGamma.getAlfa().getId(),
            savedGamma.getBeta().getId(),
            savedGamma.getEntero()
        );

        eventPublisher.publish("gamma.created", eventPayload);
        
    	return savedGamma;
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
}
