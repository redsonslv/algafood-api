package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {

	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
}
