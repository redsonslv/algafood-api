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

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "jpa_cad_itens_pedido")
public class ItemPedido {

	@Id
	@Column(name = "num_id_ipd")
	private Long id;
	
	@Column(name = "qtd_item_ipd")
	private Integer quantidade;
	
	@Column(name = "vlr_unit_ipd")
	private BigDecimal precoUnitario;
	
	@Column(name = "vlr_tot_ipd")
	private BigDecimal precoTotal;
	
	@Column(name = "des_obs_ipd")
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "cod_id_prd_ipd", nullable = false)
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "num_id_pdd_ipd", nullable = false)
	private Pedido pedido;
	
	public void calcularPrecoTotal() {
		Integer qtd = this.getQuantidade();
		BigDecimal preco = this.getPrecoUnitario();
		
		System.out.println(String.format("%d - %f",qtd,preco));
		
		if(qtd == null) {
			qtd = 0;
		}
		
		if(preco == null) {
			preco = BigDecimal.ZERO;
		}
		
		this.setPrecoTotal(preco.multiply(new BigDecimal(qtd)));
	}
	
}
