package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

	public Long clienteId;

	public Long restauranteId;
	
	public StatusPedido statusPedido;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public OffsetDateTime dataCriacaoFim;
}
