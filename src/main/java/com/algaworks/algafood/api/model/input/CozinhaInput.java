package com.algaworks.algafood.api.model.input;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInput {

	private Long id;
	
	@NotBlank
	private String nome;
	
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
