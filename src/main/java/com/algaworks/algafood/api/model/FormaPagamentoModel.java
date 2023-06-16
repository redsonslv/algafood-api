package com.algaworks.algafood.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {
	@NotNull
	private Long Id;
	
	@NotBlank
	private String descricao;
}
