package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO 
		= "Grupo de código %s não pode ser removido, pois está em uso";

	private static final String MSG_GRUPO_NAO_ENCONTRADO 
		= "Não existe um cadastro de grupo com código %s";	
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
		
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(String grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(
				String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	public Grupo buscarOuFalhar(String grupoId) {
		return grupoRepository.findById(grupoId)
			.orElseThrow(() -> new GrupoNaoEncontradoException(
					String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId)));
	}
	
	@Transactional
	public void associarPermissao(String grupoId, Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		Grupo grupo = buscarOuFalhar(grupoId);
		
		grupo.getPermissao().add(permissao);
	}	
	
	@Transactional
	public void desassociarPermissao(String grupoId, Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		Grupo grupo = buscarOuFalhar(grupoId);
		
		grupo.getPermissao().remove(permissao);
	}	
}
