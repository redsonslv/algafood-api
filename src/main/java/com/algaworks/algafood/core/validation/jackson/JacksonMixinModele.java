package com.algaworks.algafood.core.validation.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.mixin.CidadeMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModele extends SimpleModule{
	/*
	 * Vincula as ANOTAÇÕES do Jackson com a classe RESTAURANTE
	 * 
	 * */
	
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModele() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}

}
