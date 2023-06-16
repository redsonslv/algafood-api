package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService cadastro;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@GetMapping
	public List<CozinhaModel> listar(){
		return  cozinhaModelAssembler.toCollectionModel(repository.findAll());
	}
	
/*	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(repository.findAll().listar());
	}
*/
	
	@GetMapping("/{cozinhaId}")
	public  CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelAssembler.toModel(cadastro.buscarOuFalhar(cozinhaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody CozinhaInput cozinhaInput) {
		return cozinhaModelAssembler.toModel(
					repository.save(
								cozinhaInputDisassembler.toDomainObject(cozinhaInput)));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable @Valid Long cozinhaId,@RequestBody CozinhaInput cozinhaInput){
		Cozinha cozinhaAtual = cadastro.buscarOuFalhar(cozinhaId);
		
		BeanUtils.copyProperties(cozinhaInputDisassembler.toDomainObject(cozinhaInput), cozinhaAtual, "id");
		
		return cozinhaModelAssembler.toModel(
					cadastro.salvar(cozinhaAtual));
	}
	
	@GetMapping("/por-nome")
	public List<CozinhaModel> cozinhasPorNome(String nome){
		return cozinhaModelAssembler.toCollectionModel(repository.findByNome(nome));		
	}
	
//	@DeleteMapping("/{cozinhaId}")
//	public ResponseEntity<Cozinha> apagar(@PathVariable Long cozinhaId){
//		try {
//			repository.deleteById(cozinhaId);
//				
//			return ResponseEntity.noContent().build();
//		}
//		catch(EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastro.excluir(cozinhaId);
	}
}
