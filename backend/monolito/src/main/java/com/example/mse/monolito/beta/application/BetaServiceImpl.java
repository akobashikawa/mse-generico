package com.example.mse.monolito.beta.application;

import com.example.mse.monolito.beta.domain.Beta;
import com.example.mse.monolito.beta.infrastructure.repository.BetaDataSource;
import com.example.mse.monolito.beta.infrastructure.repository.DatabaseBetaDataSource;
import com.example.mse.monolito.beta.infrastructure.repository.JsonBetaDataSource;
import com.example.mse.monolito.beta.infrastructure.repository.RestBetaDataSource;
import com.example.mse.monolito.nats.NatsEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BetaServiceImpl implements BetaService {
	
	@Autowired
	private NatsEventPublisher eventPublisher;

    private final BetaDataSource dataSource;

    public BetaServiceImpl(
        DatabaseBetaDataSource databaseBetaDataSource,
        JsonBetaDataSource jsonBetaDataSource,
        RestBetaDataSource restBetaDataSource,
        @Value("${beta.datasource.type:database}") String dataSourceType
    ) {
    	switch (dataSourceType) {
	        case "database":
	            this.dataSource = databaseBetaDataSource;
	            break;
	        case "json":
	            this.dataSource = jsonBetaDataSource;
	            break;
	        case "rest":
	            this.dataSource = restBetaDataSource;
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown data source type: " + dataSourceType);
	    }
    }

    public List<Beta> findAll() {
        return dataSource.findAll();
    }

    public Optional<Beta> findById(Long id) {
        return dataSource.findById(id);
    }

    public Beta save(Beta beta) {
        Beta savedItem = dataSource.save(beta);
        
        // Publicar evento en NATS
    	Map<String, Object> payload = new HashMap<>();
    	payload.put("id", savedItem.getId());
    	payload.put("texto", savedItem.getTexto());
    	payload.put("entero", savedItem.getEntero());
    	payload.put("decimal", savedItem.getDecimal());

        eventPublisher.publish("beta.created", payload);
        
        return savedItem;
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
    
    @Override
    public Beta updateEntero(Long id, Integer entero) {
        Beta found = dataSource.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beta no encontrado con id: " + id));
        
        Integer nuevoEntero = found.getEntero() + entero;
        found.setEntero(nuevoEntero);
        
        return dataSource.save(found);
    }
}
