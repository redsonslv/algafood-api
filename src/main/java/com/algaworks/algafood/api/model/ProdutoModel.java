package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoModel {

	private String id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private String situacao;
	
}