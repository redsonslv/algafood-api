package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CadastroFotoProdutoService {

	@Autowired
	private FotoStorageService fotoService;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		String produtoId = foto.getProduto().getId();
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		String nomeNovoArquivo = fotoService.gerarNomeArquivo(foto.getNomeArquivo());
		
		foto.setNomeArquivo(nomeNovoArquivo);
		
		if (fotoExistente.isPresent()) {
			String nomeArqivoExistente = fotoExistente.get().getNomeArquivo();

			// quando o mapeamento é OneToOne no UPDATE usar merge
			foto = produtoRepository.merge(foto);
			
			// remove a foto anterior
			fotoService.remover(nomeArqivoExistente);
		} else {
			// quando o mapeamento é OneToOne no INSERT usar persist
			foto = produtoRepository.persist(foto);			
		}
		
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
				
		fotoService.armazenar(novaFoto);
		
		return foto;
	}
	
	@Transactional
	public FotoProduto buscarOuFalhar(Long restauranteId, String produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
			.orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
	@Transactional
	public void excluirFotoProduto(Long restauranteId, String produtoId) {
		FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
		
		String nomeArquivo = foto.getNomeArquivo();
		
		produtoRepository.delete(foto);
		
		fotoService.remover(nomeArquivo);
		
		produtoRepository.flush();
		
	}

	
}
