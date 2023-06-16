package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String pedidoCodigo) {
		super(String.format("Pedido %s não existe.", pedidoCodigo));
	}		
}
