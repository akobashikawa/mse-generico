package com.example.mse.monolito.gamma.domain;


import com.example.mse.monolito.alfa.domain.Alfa;
import com.example.mse.monolito.beta.domain.Beta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gamma")
@Data
public class Gamma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String texto;
	private Integer entero;
	private Double decimal;
	
	@ManyToOne
	@JoinColumn(name = "alfa_id", nullable = false)
	private Alfa alfa;
	
	@ManyToOne
	@JoinColumn(name = "beta_id", nullable = false)
	private Beta beta;

}
