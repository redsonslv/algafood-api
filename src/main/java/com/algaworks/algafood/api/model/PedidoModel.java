package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.EnderecoPedido;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter") isso era para consulta com JacksonFilter no PedidoController
@Getter
@Setter
public class PedidoModel {
	private String codigo;
	private BigDecimal taxaFrete;
	private BigDecimal valorSubtotal;
	private BigDecimal valorTotal;
	private String statusPedido;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
 	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
	private FormaPagamentoModel formaPagamento;
	private EnderecoPedido enderecoEntrega;
	private List<ItemPedidoModel> itens = new ArrayList<>();
}
