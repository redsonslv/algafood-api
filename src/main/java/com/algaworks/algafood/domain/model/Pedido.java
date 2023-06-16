package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "jpa_cad_pedido")
public class Pedido extends AbstractAggregateRoot<Pedido>{
	
	@Id
	@Column(name = "num_id_pdd")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include	
	private Long id;

	@Column(name = "cod_uuid_pdd")
	private String codigo;
	
	@Column(name = "vlr_taxa_frete_pdd")
	private BigDecimal taxaFrete;

	@Column(name = "vlr_sub_total_pdd")
	private BigDecimal valorSubtotal;
	
	@Column(name = "vlr_total_pdd")
	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	@Column(name = "cod_sta_pdd")
	private StatusPedido statusPedido = StatusPedido.CRIADO;
	
	@CreationTimestamp
	@Column(name = "dta_inc_pdd")
	private OffsetDateTime dataCriacao;

	@UpdateTimestamp
	@Column(name = "dta_confrm_pdd")
	private OffsetDateTime dataConfirmacao;
	
	@Column(name = "dta_canc_pdd")
	private OffsetDateTime dataCancelamento;
	
	@Column(name = "dta_entrg_pdd")
	private OffsetDateTime dataEntrega;
	
	@ManyToOne
	@JoinColumn(name = "num_id_res_pdd", nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "num_id_usu_pdd", nullable = false)
	private Usuario cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "num_id_fpg_pdd" ,nullable = false)
	private FormaPagamento formaPagamento;
	
	@JsonIgnore
	@Embedded
	private EnderecoPedido enderecoEntrega;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	// PrePersist funciona como o PRE-INSERT 
	@PrePersist
	private void gerarCodigo() {
		setCodigo(java.util.UUID.randomUUID().toString());
	}	

	public void calcularSubTotal() {
		// executa a calcularPrecoTotal da classe ItemPedido para cada item do pedido
		this.getItens().forEach(ItemPedido::calcularPrecoTotal);
		
		// faz a somatoria do valor total dos itens e atribui no subtotal
		this.valorSubtotal = this.getItens().stream()
				.map(item -> item.getPrecoTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		// faz subtotal + frete e atribui ao total
		this.valorTotal = this.valorSubtotal.add(this.taxaFrete);
		
	}

	public void emitir() {
		this.statusPedido = StatusPedido.CRIADO;				
		setDataCriacao(OffsetDateTime.now());
	}
	
	public void confirmar() {
		setStatusPedido(StatusPedido.CONFIRMADO);				
		setDataConfirmacao(OffsetDateTime.now());
		
		registerEvent(new PedidoConfirmadoEvent(this));
		
	}
	
	public void entregar() {
		setStatusPedido(StatusPedido.ENTREGUE);				
		setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		setStatusPedido(StatusPedido.CANCELADO);				
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	private void setStatusPedido(StatusPedido novoStatus) {		
		if(!statusPedido.transicaoStatusEValida(novoStatus)) {
			throw new NegocioException(
					String.format("Status do pedido %s não pode ser alterado de %s para %s", 
								  getCodigo(),getStatusPedido().getDescricao(),novoStatus.getDescricao()));
		}
		statusPedido = novoStatus;
	}
}
