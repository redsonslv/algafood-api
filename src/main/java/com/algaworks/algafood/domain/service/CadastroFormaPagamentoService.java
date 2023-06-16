package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_FORMA_PAGAMENTO_EM_USO 
		= "Forma de Pagamento de código %s não pode ser removido, pois está em uso";

	private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO 
		= "Não existe um cadastro de forma de pagamento com código %s";	
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
		
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void excluir(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(
				String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO, formaPagamentoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}

	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
			.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(
					String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO,formaPagamentoId)));
	}
}
