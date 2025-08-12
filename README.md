## Produto Service com Resilience4j

Este projeto Spring Boot é um serviço REST para gerenciamento de produtos, que integra chamadas a um serviço externo de avaliações com Resilience4j para circuit breaker e fallback com cache local.

---

## O serviço de avaliações está disponível em:
https://github.com/emerson998/avaliacao-service

## Tecnologias e Dependências

    Spring Boot 2.7.3 — Framework principal para desenvolvimento da aplicação.
    Spring Boot Starter Web — Criação de APIs REST.
    Spring Boot Starter Data JPA 
    Spring Boot Starter Actuator
    Spring Cloud Starter Circuitbreaker Resilience4j 
    H2 Database 
    Lombok
