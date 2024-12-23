package com.example.mse.monolito.gamma.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mse.monolito.gamma.application.GammaService;
import com.example.mse.monolito.gamma.domain.Gamma;

@RestController
@RequestMapping("/api/gamma")
public class GammaController {
	
	@Autowired
	private GammaService gammaService;

    @GetMapping
    public List<Gamma> getAll() {
        return gammaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gamma> getById(@PathVariable Long id) {
        return gammaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Gamma create(@RequestBody Gamma gamma) {
    	gamma.setId(null);
        return gammaService.save(gamma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gamma> update(@PathVariable Long id, @RequestBody Gamma gamma) {
        return gammaService.findById(id)
                .map(existingGamma -> {
                    gamma.setId(existingGamma.getId());
                    return ResponseEntity.ok(gammaService.save(gamma));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (gammaService.findById(id).isPresent()) {
            gammaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
