package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.algaworks.algafood.api.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {
	
	@JsonView({RestauranteView.Resumo.class})
	private Long id;
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	@NotBlank
	private String nome;
	
	@JsonView({RestauranteView.Resumo.class})
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaModel cozinha;
	
	@NotBlank
	private String situacao;
	
	@NotBlank
	private String aberto;
	
	private EnderecoModel endereco;
}
