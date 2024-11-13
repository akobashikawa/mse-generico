package com.example.mse.monolito.beta.application;

import com.example.mse.monolito.beta.domain.Beta;
import com.example.mse.monolito.beta.domain.BetaDataSource;
import com.example.mse.monolito.beta.domain.BetaService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BetaServiceImpl implements BetaService {

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
        return dataSource.save(beta);
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
}
