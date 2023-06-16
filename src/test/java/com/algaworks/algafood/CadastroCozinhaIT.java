package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {
	/*
	 * Testes de Integracao
	 */
	
/*	
	@Autowired
	private CadastroCozinhaService cadastrCozinha;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		// cenario
		 Cozinha novaCozinha = new Cozinha();
		 
		 novaCozinha.setNome("Chinesa");
		
		// acao
		 novaCozinha = cadastrCozinha.salvar(novaCozinha);
		
		// validacao
		 assertThat(novaCozinha).isNotNull();
		 assertThat(novaCozinha.getId()).isNotNull();		 
	}
	
	@Test()
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		 Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			 cadastrCozinha.excluir(4L);			 
		 }); 
	}

	@Test()
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		
		 Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> {
			 cadastrCozinha.excluir(100L);			 
		 }); 
	}
*/	
	/*
	 * Testes de API - Chamadas da API RestFull 
	 */

	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinha() {
		RestAssured.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());

	}
	
	@Test
	public void deveConterQuatroCozinhas_QuandoConsultarCozinha() {
		RestAssured.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(17)) // verifica se joson retornou 17 itens (cozinhas)
				.body("nome", Matchers.hasItems("Brasileira","Chinesa")); // verifica se a tag nome tem os valores Brasileira e Chinesa 

	}
/*	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
				.body("{ \"nome\": \"Chinesa\" }")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
*/
	
	@Test
	public void deveRetornarStatusEDadosCorretos_QuandoConsultarCozinha() {
		RestAssured.given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome",equalTo("Indiana"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
