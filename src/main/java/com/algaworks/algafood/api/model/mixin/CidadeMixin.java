package com.algaworks.algafood.api.model.mixin;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
public class CidadeMixin{

	@JsonIgnoreProperties(value = {"nome"})
	private Estado estado;	

}
