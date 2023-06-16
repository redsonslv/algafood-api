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
@Table(name = "jpa_tab_forma_pgto")
@Data
public class FormaPagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_id_fpg")
	@EqualsAndHashCode.Include
	private Long Id;
	
	@Column(name = "des_forma_pgto_fpg", length = 20)
	private String descricao;
}
