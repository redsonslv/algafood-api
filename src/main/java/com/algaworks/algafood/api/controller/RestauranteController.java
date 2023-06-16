package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.exceptionhandler.ValidacaoException;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private SmartValidator smartValidator;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler; 
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String projecao){
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		List<RestauranteModel> restauranteModel = restauranteModelAssembler.toCollectionModel(restaurantes); 
		
		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteModel);
		
		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
		
		if ("completo".equals(projecao)) {
			restaurantesWrapper.setSerializationView(null);
		}
		else if("apenas-nome".equals(projecao)) {
			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
		}
		
		return restaurantesWrapper;
		
	}	
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping("/listar")
	public ResponseEntity<List<RestauranteModel>> listar() {
		List<RestauranteModel> restaurantesModel = restauranteModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
				.body(restaurantesModel);
	}
	
	
	
//	@GetMapping
//	public List<RestauranteModel> listar(){
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
//		
//	@GetMapping(params = "projecao=resumido")
//	@JsonView(RestauranteView.Resumo.class)
//	public List<RestauranteModel> listarResumido(){
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
//	
//	@GetMapping(params = "projecao=apenas-nome")
//	@JsonView(RestauranteView.ApenasNome.class)
//	public List<RestauranteModel> listarApenasNome(){
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);		
		return restauranteModelAssembler.toModel(restaurante);
	}

//	@GetMapping("/por-nome")
//	public  List<Restaurante> buscar(String nome, Long cozinhaId) {
//		return restauranteRepository.consultarPorNome(nome, cozinhaId);
//		/*
//		List<Restaurante> restaurante = restauranteRepository.consultarPorNome(nome, cozinhaId);		
//		if (restaurante != null) {
//			return ResponseEntity.ok().build();
//		}
//		else {
//			return ResponseEntity.notFound().build();
//		}*/
//	}
//	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restaurante) {
		System.out.println("RestauranteController.adicionar");
		try {
			return restauranteModelAssembler.toModel(
					cadastroRestaurante.salvar(
							restauranteInputDisassembler.toDomainObject(restaurante)));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId
			,@RequestBody @Valid RestauranteInput restauranteInput){

		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
//	@DeleteMapping("/{restauranteId}")
//	public void apagar(@PathVariable Long restauranteId){
//		try {
//			restauranteRepository.deleteById(restauranteId);
//				
//			return ResponseEntity.noContent().build();
//		}
//		catch(EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//		
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
	
	@PatchMapping("/{restauranteId}")
	public RestauranteModel atualizarParcial(@PathVariable @Valid Long restauranteId,
			@RequestBody Map<String, Object> campos,
			HttpServletRequest request){

		System.out.println("Patch - RestauranteController.atualizarParcial");
				
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		merge(campos, restauranteAtual, request);
		
		validate(restauranteAtual,"restaurante");
		
		return atualizar(restauranteId,restauranteInputDisassembler.toInputObject(restauranteAtual));		
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable @Valid Long restauranteId, 
			HttpServletRequest request){
		cadastroRestaurante.ativar(restauranteId);		
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable @Valid Long restauranteId, 
			HttpServletRequest request){
 		cadastroRestaurante.inativar(restauranteId);		
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public RestauranteModel abrir(@PathVariable Long restauranteId, HttpServletRequest request) {
		return restauranteModelAssembler.toModel(cadastroRestaurante.abrir(restauranteId));		
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public RestauranteModel fechar(@PathVariable Long restauranteId, HttpServletRequest request) {
		return restauranteModelAssembler.toModel(cadastroRestaurante.fechar(restauranteId));		
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante,objectName);
		
		smartValidator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()){
			throw new ValidacaoException(bindingResult); 
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		
		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade)->{
				
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
						
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				System.out.println(nomePropriedade + " = " + novoValor);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
				
				
			});
		}catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			
			throw new HttpMessageNotReadableException(e.getMessage(),rootCause, servletServerHttpRequest); 
		}
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}
		 
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}
}
