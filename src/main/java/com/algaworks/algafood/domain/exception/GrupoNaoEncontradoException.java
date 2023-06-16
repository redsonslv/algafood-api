package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
//	public EstadoNaoEncontradoException(String estadoId) {
//		this(String.format("Não existe um cadastro de estado com código %s", estadoId));
//	}		
}
