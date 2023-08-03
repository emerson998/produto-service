package com.algaworks.example.resilience4j.produtos.infra.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.algaworks.example.resilience4j.produtos.cliente.avaliacao.AvaliacaoClient;
import com.algaworks.example.resilience4j.produtos.cliente.avaliacao.AvaliacaoModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class AvaliacaoClientImpl implements AvaliacaoClient {

	private final Map<Long , List<AvaliacaoModel>> cache = new HashMap<>();

	private final RestTemplate restTemplate;
	
	private static final String API_URL = UriComponentsBuilder
			.fromHttpUrl("http://localhost:8090/avaliacoes")
			.queryParam("produtoId", "{produtoId}")
			.encode()
			.toUriString();
	
	public AvaliacaoClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@CircuitBreaker(name = "produto" , fallbackMethod = "buscarTodosPorProdutoNoCache")
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		final List<AvaliacaoModel> avaliacoes = executarRequisicao(produtoId);
		return avaliacoes;
	}

	private List<AvaliacaoModel> executarRequisicao(Long produtoId) {
		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("produtoId", produtoId);

		log.info("Buscando avaliações");
		final AvaliacaoModel[] avaliacoes;
		
		try {
			avaliacoes = restTemplate.getForObject(API_URL, AvaliacaoModel[].class, parametros);
		} catch (Exception e) {
			log.error("Erro ao buscar avaliações");
			throw e;
		}
		
		log.info("Alimentando cache");
		cache.put(produtoId, Arrays.asList(avaliacoes));

		return Arrays.asList(avaliacoes);
	}
	
	
	 protected List<AvaliacaoModel> buscarTodosPorProdutoNoCache(Long produtoId, Throwable e) {
		log.info("Buscando no cache");
		return cache.getOrDefault(produtoId, new ArrayList<>());
	}
}