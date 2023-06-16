package com.algaworks.algafood.domain.exception;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public ItemPedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ItemPedidoNaoEncontradoException(Long itemPedidoId) {
		this(String.format("Item %d não existe.", itemPedidoId));
	}		
}
