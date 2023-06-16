package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoNaoEncontradoException(Long restauranteId, String produtoId) {
		this(String.format("Não existe foto para o produto %s e restaurante %d", 
				produtoId, restauranteId));
	}
	
}