package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	@Column(name = "cod_cep_res", length = 8)
	private String cep;
	
	@Column(name = "nom_logr_res", length = 70)
	private String logradouro;
	
	@Column(name = "num_imvl_res", length = 7)
	private String numero;
	
	@Column(name = "nom_bair_res", length = 20)
	private String bairro;
	
	@Column(name = "cod_compl_res", length = 20)
	private String complemento;
	
	@ManyToOne
	@JoinColumn(name = "num_id_mun_res")
	private Cidade cidade;	

}
