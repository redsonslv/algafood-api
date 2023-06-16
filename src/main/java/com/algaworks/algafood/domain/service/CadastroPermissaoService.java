package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	private static final String MSG_PERMISSAO_EM_USO 
		= "Permissão de código %s não pode ser removido, pois está em uso";

	private static final String MSG_PERMISSAO_NAO_ENCONTRADO 
		= "Não existe um cadastro de permissão com código %s";	
	
	@Autowired
	private PermissaoRepository permissaoRepository;
		
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	public void excluir(Long permissaoId) {
		try {
			permissaoRepository.deleteById(permissaoId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new PermissaoNaoEncontradaException(
				String.format(MSG_PERMISSAO_NAO_ENCONTRADO, permissaoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_PERMISSAO_EM_USO, permissaoId));
		}
	}

	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
			.orElseThrow(() -> new PermissaoNaoEncontradaException(
					String.format(MSG_PERMISSAO_NAO_ENCONTRADO, permissaoId)));
	}	
}
