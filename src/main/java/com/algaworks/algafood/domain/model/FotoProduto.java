package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "jpa_cad_foto_produto")
public class FotoProduto {
	
	@Id
	@Column(name = "cod_id_prd_fpd")
	@EqualsAndHashCode.Include
	private String id;

	@Column(name = "nom_arq_fpd")
	private String nomeArquivo;
	
	@Column(name = "des_arq_fpd")
	private String descricao;
	
	@Column(name = "cod_tipo_arq_fpd")
	private String contentType;
	
	@Column(name = "num_tam_fpd")
	private Long tamanho;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_id_prd_fpd", referencedColumnName = "cod_id_prd")
	private Produto produto;
	
	public Long getRestauranteId() {
		if (getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		
		return null;
	}	
}
