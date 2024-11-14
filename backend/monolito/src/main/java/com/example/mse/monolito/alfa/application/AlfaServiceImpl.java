package com.example.mse.monolito.alfa.application;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.alfa.infrastructure.repository.AlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.DatabaseAlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.JsonAlfaDataSource;
import com.example.mse.monolito.alfa.infrastructure.repository.RestAlfaDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlfaServiceImpl implements AlfaService {

    private final AlfaDataSource dataSource;

    public AlfaServiceImpl(
        DatabaseAlfaDataSource databaseAlfaDataSource,
        JsonAlfaDataSource jsonAlfaDataSource,
        RestAlfaDataSource restAlfaDataSource,
        @Value("${alfa.datasource.type:database}") String dataSourceType
    ) {
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
        return dataSource.save(alfa);
    }

    public void deleteById(Long id) {
        dataSource.deleteById(id);
    }
    
    @Override
    public Alfa updateEntero(Long id, Integer nuevoEntero) {
        // Paso 1: Buscar el Alfa por ID
        Alfa alfa = dataSource.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alfa no encontrado con ID: " + id));
        
        // Paso 2: Actualizar el campo entero
        alfa.setEntero(nuevoEntero);
        
        // Paso 3: Guardar la entidad actualizada
        return dataSource.save(alfa);
    }
}
