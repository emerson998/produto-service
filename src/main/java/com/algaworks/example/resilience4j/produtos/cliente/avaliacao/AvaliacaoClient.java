package com.algaworks.example.resilience4j.produtos.cliente.avaliacao;

import java.util.List;

public interface AvaliacaoClient {

	List<AvaliacaoModel> buscarTodosPorProduto(Long productId);
}
