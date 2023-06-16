package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
	private String produtoId;
	private Long quantidade;
	private String observacao;
}
