package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
	@NotBlank
	private String cep;
	
	@NotBlank
	private String Logradouro;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String bairro;
	
	private String complemento;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;	

}
