package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class EnderecoPedido {
	
	@Column(name = "cod_cep_pdd", length = 8)
	private String cep;
	
	@Column(name = "nom_logr_pdd", length = 70)
	private String logradouro;
	
	@Column(name = "num_imvl_pdd", length = 7)
	private String numero;
	
	@Column(name = "nom_bair_pdd", length = 20)
	private String bairro;
	
	@Column(name = "cod_compl_pdd", length = 20)
	private String complemento;
	
	@ManyToOne
	@JoinColumn(name = "num_id_mun_pdd")
	private Cidade cidade;	

}
