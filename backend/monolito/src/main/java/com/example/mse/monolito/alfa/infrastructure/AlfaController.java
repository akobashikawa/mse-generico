package com.example.mse.monolito.alfa.infrastructure;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.alfa.domain.AlfaService;

@RestController
@RequestMapping("/api/alfa")
public class AlfaController {
	
	private final AlfaService alfaService;

    public AlfaController(AlfaService alfaService) {
        this.alfaService = alfaService;
    }

    @GetMapping
    public List<Alfa> getAll() {
        return alfaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alfa> getById(@PathVariable Long id) {
        return alfaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Alfa create(@RequestBody Alfa alfa) {
        return alfaService.save(alfa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alfa> update(@PathVariable Long id, @RequestBody Alfa alfa) {
        return alfaService.findById(id)
                .map(existingAlfa -> {
                    alfa.setId(existingAlfa.getId());
                    return ResponseEntity.ok(alfaService.save(alfa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (alfaService.findById(id).isPresent()) {
            alfaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
