package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	private static final String MSG_PEDIDO_NAO_ENCONTRADO 
	= "Não existe um cadastro de pedido com código %s";
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	CadastroRestauranteService restauranteService;
	
	@Autowired
	CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	CadastroUsuarioService usuarioService;
	
	@Autowired
	CadastroCidadeService cidadeService;
	
	@Autowired
	CadastroProdutoService produtoService;
	
	public Pedido buscarOuFalhar(String pedidoCodigo) {
		return pedidoRepository.findByCodigo(pedidoCodigo)
			.orElseThrow(() -> new PedidoNaoEncontradoException(
					String.format(MSG_PEDIDO_NAO_ENCONTRADO, pedidoCodigo)));
	}
	
	//@Transactional
	public Pedido emitir(Pedido pedido) {
		validarItens(pedido);

		validarPedido(pedido);
		
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	private void validarPedido(Pedido pedido) {
		Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId()); 
		Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		pedido.emitir();

		pedido.getEnderecoEntrega().setCidade(cidade);		
		
		pedido.setCliente(cliente);
		
		pedido.setRestaurante(restaurante);
		
		pedido.setFormaPagamento(formaPagamento);

		pedido.setTaxaFrete(restaurante.getTaxaFrete());
		
		pedido.calcularSubTotal();
	}
	
	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarOuFalhar(
					pedido.getRestaurante().getId(),
					item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido = buscarOuFalhar(pedidoCodigo);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void entregarPedido(String pedidoCodigo) {
		Pedido pedido = buscarOuFalhar(pedidoCodigo);		
		pedido.entregar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelarPedido(String pedidoCodigo) {
		Pedido pedido = buscarOuFalhar(pedidoCodigo);
		pedido.cancelar();

		pedidoRepository.save(pedido);
	}	
}
