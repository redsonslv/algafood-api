package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;
	
	@Autowired
	GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public List<UsuarioModel> listar(){
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(usuarioId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody UsuarioInput usuarioInput) {
		return usuarioModelAssembler.toModel(
				cadastroUsuarioService.salvar(
						usuarioInputDisassembler.toDomainObject(usuarioInput)));
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		BeanUtils.copyProperties(usuarioInputDisassembler.toDomainObject(usuarioInput), usuarioAtual, "id","dataInclusao");
		
		return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
		
	}
	
	@DeleteMapping("/{usuarioId}")
	public void apagar(@PathVariable Long usuarioId){
		cadastroUsuarioService.excluir(usuarioId);
	}
	
	@GetMapping("/{usuarioId}/grupos")
	public List<GrupoModel> listarGrupos(@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);		
		return grupoModelAssembler.toCollectionModel(usuario.getGrupo());		
	}
	
	@PutMapping("/{usuarioId}/grupos/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long usuarioId, @PathVariable String grupoId){
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("/{usuarioId}/grupos/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@ PathVariable Long usuarioId, @PathVariable String grupoId) {
		cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}
}
