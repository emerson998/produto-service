## API de Produtos com Resilience4j

Este projeto implementa uma API REST para gerenciamento de produtos usando Spring Boot. A principal característica deste componente é sua integração com o Resilience4j, que garante a resiliência da aplicação ao lidar com falhas em chamadas a serviços externos, como uma API de avaliações.

## Visão Geral

O ProdutoController é responsável por expor os endpoints da API. Ele interage com um ProdutoRepository para buscar informações de produtos e com um AvaliacaoClient para obter as avaliações de cada produto. A chamada ao cliente de avaliações é onde a resiliência é aplicada, protegendo a API de produtos de falhas no serviço de avaliações.

### O serviço de avaliações está disponível em:
https://github.com/emerson998/avaliacao-service

## Endpoints

### GET /produtos

Retorna uma lista completa de todos os produtos cadastrados.

Descrição: Este endpoint busca e retorna uma lista de produtos, mas sem suas avaliações. Essa abordagem otimiza a performance, evitando chamadas desnecessárias ao serviço de avaliações para listagens gerais.

### GET /produtos/{produtoId}

Retorna os detalhes de um produto específico, incluindo suas avaliações.

Parâmetros:
Parâmetro	Tipo	Descrição	Obrigatório
produtoId	Long	O ID do produto a ser buscado	Sim

Descrição: Este endpoint busca um produto pelo seu ID e, em seguida, faz uma chamada ao AvaliacaoClient para buscar as avaliações associadas a ele. O Resilience4j é utilizado nesta chamada para garantir que, se o serviço de avaliações estiver lento ou indisponível, nossa API de produtos continue funcionando de maneira estável.

 ### Estratégias de Resiliência (Resilience4j)

Embora a implementação explícita do Resilience4j não esteja visível no controlador, ele é aplicado no AvaliacaoClient. As estratégias comuns que seriam usadas incluem:

    Circuit Breaker: Protege o sistema de avaliações de ser sobrecarregado por falhas. Se as chamadas começarem a falhar, o circuito é "aberto", e as próximas chamadas falham instantaneamente, dando tempo para o serviço de avaliações se recuperar.

    Retry: Permite que a aplicação tente novamente uma chamada ao serviço de avaliações um número configurável de vezes em caso de falhas temporárias.

    Time Limiter: Define um tempo limite para as chamadas, garantindo que o serviço de produtos não fique bloqueado esperando por uma resposta de um serviço externo lento.
