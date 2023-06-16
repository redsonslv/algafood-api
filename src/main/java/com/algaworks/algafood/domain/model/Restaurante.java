package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete",
		descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "jpa_cad_restaurante")
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_id_res")
	private Long id;
	
	@NotBlank
	@Column(name = "nom_abre_res", length = 30, nullable = false)
	private String nome;
	
	@NotNull
	@PositiveOrZero
	@Column(name = "vlr_taxa_frete_res", nullable = false)
	private BigDecimal taxaFrete;
	
	@NotBlank
	@Column(name = "cod_situ_res", nullable = false)
	private String situacao;
	
	@NotBlank
	@Column(name = "sta_abert_res", nullable = false)
	private String aberto;
	
	@Valid
	@NotNull
	@ManyToOne //(fetch = FetchType.LAZY) // por padrao ManyToOne é EAGER, ou seja sempre executa o SELECT na tabela relacionada
	                                    // com o LAZY o SELECT na tabela relacionada é por demanda, ou seja, o SELECT na tabela 
	                                    // relacinada só é realizada quando a informação sobre cozinha é requerida 
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@JoinColumn(name = "num_id_coz_res", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(name = "dta_inc_res", columnDefinition = "date not null")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(name = "dta_atu_res", columnDefinition = "date not null")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany // por default ManyToMany é com fetch LAZY, ou seja só executa o SELECT 
	            //na tabela relacionado quando a informação é solicitada
	@JoinTable(name = "jpa_rel_res_fpg", 
		joinColumns = @JoinColumn(name = "num_id_res_rfp"),
		inverseJoinColumns = @JoinColumn(name = "num_id_fpg_rfp"))
//	private List<FormaPagamento> formasPagamento = new ArrayList<>();
//  o HashSet nao permite objetos duplicados
	private Set<FormaPagamento> formasPagamento = new HashSet<>();	
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "jpa_rel_usu_res", 
			joinColumns = @JoinColumn(name = "num_id_res_usr"),
			inverseJoinColumns = @JoinColumn(name = "num_id_usu_usr"))
	private Set<Usuario> usuario = new HashSet<>();	
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public boolean adicionarUsuario(Usuario usuario) {
		return getUsuario().add(usuario);		
	}
	
	public boolean removerUsuario(Usuario usuario) {
		return getUsuario().remove(usuario);		
	}
	
	public void ativar() {
		setSituacao("AT");
	}
	
	public void inativar() {
		setSituacao("IN");
	}
}
