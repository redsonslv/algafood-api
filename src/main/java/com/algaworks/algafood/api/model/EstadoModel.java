package com.algaworks.algafood.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel {
	
	@NotBlank
	private String id; 	

	@NotBlank
    private String nome;
	
}
