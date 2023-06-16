package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "jpa_cad_produto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {
	
	@Id
	@Column(name = "cod_id_prd", length = 10, nullable = false)
	@EqualsAndHashCode.Include
	private String id;
	
	@Column(name = "nom_prod_prd", length = 50, nullable = false)
	private String nome;
	
	@Column(name = "des_prod_prd", length = 500, nullable = false)
	private String descricao;
	
	@Column(name = "vlr_unit_prd", nullable = false)
	private BigDecimal preco;
	
	@Column(name = "cod_situ_prd", columnDefinition = "varchar2(2)", nullable = false)
	private String situacao;
	
	@ManyToOne
	@JoinColumn(name = "num_id_res_prd", nullable = false)
	private Restaurante restaurante;
}
