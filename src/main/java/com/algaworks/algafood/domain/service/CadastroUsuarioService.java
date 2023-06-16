package com.algaworks.algafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService{

	private static final String MSG_USUARIO_EM_USO 
		= "Usuário de código %s não pode ser removido, pois está em uso";

	private static final String MSG_USUARIO_NAO_ENCONTRADO 
		= "Não existe um cadastro de Usuário com código %d";	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != usuario.getId()) {
			throw new NegocioException(
					String.format("Já existe um usuário com o email %s.", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void excluir(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(
				String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}

	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(
					String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId)));
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, String grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.getGrupo().add(grupo);				
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, String grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.getGrupo().remove(grupo);
	}
}
