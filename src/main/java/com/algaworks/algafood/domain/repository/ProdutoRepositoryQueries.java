package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);

	FotoProduto merge(FotoProduto foto);
	
	FotoProduto persist(FotoProduto foto);
	
	void delete(FotoProduto foto);		
}
