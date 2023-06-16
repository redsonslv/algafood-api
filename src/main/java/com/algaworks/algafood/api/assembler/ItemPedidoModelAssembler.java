package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ItemPedidoModel;
import com.algaworks.algafood.domain.model.ItemPedido;

@Component
public class ItemPedidoModelAssembler {

	@Autowired
	ModelMapper modelMapper;
	
	public ItemPedidoModel toModel(ItemPedido ItemPedido) {		
		return modelMapper.map(ItemPedido, ItemPedidoModel.class);
	}
	
	public List<ItemPedidoModel> toCollectionModel(Collection<ItemPedido> itensPedido){
		return itensPedido.stream()
				.map(itemPedido -> toModel(itemPedido))
				.collect(Collectors.toList());		
	}

}
