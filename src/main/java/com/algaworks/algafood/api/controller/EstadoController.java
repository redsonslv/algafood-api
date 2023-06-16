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

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public List<EstadoModel> listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
		
	@GetMapping("/{estadoId}")
	public  EstadoModel buscar(@PathVariable String estadoId) {
		return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody EstadoInput estadoInput) {
		return estadoModelAssembler.toModel(
					cadastroEstado.salvar(
								estadoInputDisassembler.toDomainObject(estadoInput)));
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable String estadoId,@RequestBody EstadoInput estadoInput){
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		
		BeanUtils.copyProperties(estadoInputDisassembler.toDomainObject(estadoInput), estadoAtual, "id");
		
		return estadoModelAssembler.toModel(
					cadastroEstado.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	public void apagar(@PathVariable String estadoId){
		cadastroEstado.excluir(estadoId);
	}
	 
}
