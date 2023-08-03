package com.algaworks.example.resilience4j.produtos.infra.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.algaworks.example.resilience4j.produtos.cliente.avaliacao.AvaliacaoClient;
import com.algaworks.example.resilience4j.produtos.cliente.avaliacao.AvaliacaoModel;

@Component
public class AvaliacaoClientImpl implements AvaliacaoClient {

	private final Logger logger = LoggerFactory.getLogger(AvaliacaoClientImpl.class);
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
	public List<AvaliacaoModel> buscarTodosPorProduto(Long produtoId) {
		final List<AvaliacaoModel> avaliacoes = executarRequisicao(produtoId);
		return avaliacoes;
	}

	private List<AvaliacaoModel> executarRequisicao(Long produtoId) {
		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("produtoId", produtoId);

		logger.info("Buscando avaliações");
		final AvaliacaoModel[] avaliacoes;
		
		try {
			avaliacoes = restTemplate.getForObject(API_URL, AvaliacaoModel[].class, parametros);
		} catch (Exception e) {
			logger.error("Erro ao buscar avaliações");
			throw e;
		}

		return Arrays.asList(avaliacoes);
	}
	
}