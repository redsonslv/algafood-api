package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	
	private Long Id;
	
	@NotBlank
	private String nome;
	
	@NotNull
	private Estado estado;
	
}
