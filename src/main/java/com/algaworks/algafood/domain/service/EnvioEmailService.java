package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem{
		@Singular
		private Set<String> destinatarios;
		
		@NotNull
		private String assunto;
		
		@NotNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
	}

}
