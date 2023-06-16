package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroFotoProdutoService;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class ProdutoFotoController {
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	CadastroFotoProdutoService cadastroFotoProdutoService;
	
	@Autowired
	FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFotoProduto(@PathVariable Long restauranteId,
			@PathVariable String produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		FotoProduto foto = new FotoProduto();
		
		foto.setId(produto.getId());
		foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
		foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
		foto.setProduto(produto);
		
//		System.out.println(produto.getDescricao());
//		System.out.println(foto.getId());
//		System.out.println(foto.getNomeArquivo());
//		System.out.println(foto.getDescricao());
//		System.out.println(foto.getTamanho());
//		System.out.println(foto.getContentType());
		
		FotoProduto fotoSalva = cadastroFotoProdutoService.salvar(foto,fotoProdutoInput.getArquivo().getInputStream());
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel recuperarFotoProduto(@PathVariable Long restauranteId, @PathVariable String produtoId) throws IOException {
		
		FotoProduto fotoProduto = cadastroFotoProdutoService.buscarOuFalhar(restauranteId, produtoId); 		
		return fotoProdutoModelAssembler.toModel(fotoProduto);
		
	}
	
	@GetMapping
	public ResponseEntity<InputStreamResource> retornarFotoProduto(@PathVariable Long restauranteId, 
			@PathVariable String produtoId,
			@RequestHeader(name = "accept") String acceptHeader) throws IOException, HttpMediaTypeNotAcceptableException{
		try {
			FotoProduto fotoProduto = cadastroFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypesAceitas);
			
			InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
						
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStream));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, 
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if (!compativel){
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}		
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId, @PathVariable String produtoId) {
		cadastroFotoProdutoService.excluirFotoProduto(restauranteId, produtoId);	
	}
	
}
