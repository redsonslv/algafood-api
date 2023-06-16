package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
//	@GetMapping("/{pedidoCodigo}")
//	public PedidoModel buscar(@PathVariable String pedidoCodigo) {
//		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoCodigo);
//		return pedidoModelAssembler.toModel(pedido); 
//	}
	
	@GetMapping
	public List<PedidoResumoModel> pesquisar(PedidoFilter filtro){
		List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFIltro(filtro));
		
		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}
	
//	@GetMapping("/squiggly")
//	public List<PedidoResumoModel> listarSquiggly(){
//		return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
//	}
	
	
	//@JsonFIlter na classe PedidoModel permite informar na chamado do json pelo cliente quais campos ele quer no json
//	@GetMapping("/jackson-filter")
//	public MappingJacksonValue listarJacksonFIlter(@RequestParam(required = false) String campos) {
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoModel> pedidosModel = pedidoModelAssembler.toCollectionModel(pedidos);
//		
//		MappingJacksonValue pedidorWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidorWrapper.setFilters(filterProvider);
//		
//		return pedidorWrapper;
//	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel emitir(@Valid @RequestBody PedidoInput pedidoInput) {		
		Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(3L);
		
		cadastroPedidoService.emitir(pedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PutMapping("/{pedidoCodigo}/confirmar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarPedido(@PathVariable String pedidoCodigo) {
		cadastroPedidoService.confirmarPedido(pedidoCodigo);
	}
	
	@PutMapping("/{pedidoCodigo}/cancelar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable String pedidoCodigo) {
		cadastroPedidoService.cancelarPedido(pedidoCodigo);
	}
	
	@PutMapping("/{pedidoCodigo}/entregar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregarPedido(@PathVariable String pedidoCodigo) {
		cadastroPedidoService.entregarPedido(pedidoCodigo);
	}
	
}
