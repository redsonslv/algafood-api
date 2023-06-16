package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "jpa_cad_usuario")
@EqualsAndHashCode
public class Usuario {
	
	@Id
	@Column(name = "num_id_usu", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "nom_usu_usu")
	private String nome;
	
	@Column(name = "cod_email_usu")
	private String email;
	
	@Column(name = "cod_sen_usu")
	private String senha;
	
	@CreationTimestamp
	@Column(name = "dta_inc_usu", columnDefinition = "date not null")
	private OffsetDateTime dataInclusao;
	
	@UpdateTimestamp
	@Column(name = "dta_atu_usu", columnDefinition = "date not null")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "jpa_rel_usu_grp", 
			joinColumns = @JoinColumn(name = "num_id_usu_rug"),
			inverseJoinColumns = @JoinColumn(name = "cod_id_grp_rug"))
	private Set<Grupo> grupo = new HashSet<>();	
}
