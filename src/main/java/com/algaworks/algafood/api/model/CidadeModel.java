package com.algaworks.algafood.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {
	private Long Id;
	
	@NotBlank
	private String nome;
	
	@NotNull
	private Estado estado;
}
