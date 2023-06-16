package com.algaworks.algafood.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {
	private Long Id;
	
	@NotBlank
	private String nome;
		
	@NotNull
	private String estado;
}
