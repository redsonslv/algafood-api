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

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar(){
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable String grupoId) {
		return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody GrupoInput grupoInput) {
		return grupoModelAssembler.toModel(
				cadastroGrupoService.salvar(
						grupoInputDisassembler.toDomainObject(grupoInput)));
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable String grupoId, @RequestBody GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		BeanUtils.copyProperties(grupoInputDisassembler.toDomainObject(grupoInput), grupoAtual, "id");
		
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
		
	}
	
	@DeleteMapping("/{grupoId}")
	public void apagar(@PathVariable String grupoId){
		cadastroGrupoService.excluir(grupoId);
	}

}
