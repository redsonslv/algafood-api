package com.algaworks.algafood.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
}
