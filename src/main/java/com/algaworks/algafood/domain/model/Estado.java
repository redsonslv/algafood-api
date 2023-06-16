package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "jpa_tab_uf")
@Data
public class Estado {
	
	@Id
	@Column(name = "cod_uf_ufe", length = 2)
	private String id; 	
	
	@Column(name = "nom_uf_ufe", length = 20)
	private String nome;
}
