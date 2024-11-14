package com.example.mse.monolito.beta.controller;

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

import com.example.mse.monolito.beta.domain.Beta;
import com.example.mse.monolito.beta.domain.BetaService;

@RestController
@RequestMapping("/api/beta")
public class BetaController {
	
	private final BetaService betaService;

    public BetaController(BetaService betaService) {
        this.betaService = betaService;
    }

    @GetMapping
    public List<Beta> getAll() {
        return betaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beta> getById(@PathVariable Long id) {
        return betaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Beta create(@RequestBody Beta beta) {
    	beta.setId(null);
        return betaService.save(beta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beta> update(@PathVariable Long id, @RequestBody Beta beta) {
        return betaService.findById(id)
                .map(existingBeta -> {
                    beta.setId(existingBeta.getId());
                    return ResponseEntity.ok(betaService.save(beta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (betaService.findById(id).isPresent()) {
            betaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
