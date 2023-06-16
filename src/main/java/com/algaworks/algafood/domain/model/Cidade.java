package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "jpa_tab_municipio")
@Data
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_id_mun", length = 30)
	private Long Id;
	
	@Column(name = "nom_mun_mun", length = 30)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "cod_uf_mun", nullable = false)
	private Estado estado;
	
}
