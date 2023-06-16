package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "jpa_tab_permissao")
@Data
public class Permissao {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_id_per")
	private Long Id;
	
	@Column(name = "nom_permis_per", length = 20)
	@EqualsAndHashCode.Include
	private String nome; 
	
	@Column(name = "des_permis_per", length = 40)
	private String descricao;
}
