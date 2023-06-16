package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoModel {
	private String codigo;
	private BigDecimal taxaFrete;
	private BigDecimal valorSubtotal;
	private BigDecimal valorTotal;
	private String statusPedido;
	private OffsetDateTime dataCriacao;
 	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
}
