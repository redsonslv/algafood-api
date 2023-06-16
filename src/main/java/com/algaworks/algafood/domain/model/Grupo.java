package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "jpa_tab_grupo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {
	
	@Id
	@Column(name = "cod_id_grp", length = 7, nullable = false)
	@EqualsAndHashCode.Include
	private String id; 
	
	@Column(name = "des_grupo_grp", length = 20, nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "jpa_rel_grp_per", 
			joinColumns = @JoinColumn(name = "cod_id_grp_rgp"),
			inverseJoinColumns = @JoinColumn(name = "num_id_per_rgp"))
	private Set<Permissao> permissao = new HashSet<>();
}
