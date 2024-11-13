package com.example.mse.monolito.gamma.application;

import com.example.mse.monolito.gamma.domain.Gamma;
import com.example.mse.monolito.gamma.domain.GammaDataSource;
import com.example.mse.monolito.gamma.domain.GammaService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GammaServiceImpl implements GammaService {

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
        return dataSource.save(gamma);
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
}
